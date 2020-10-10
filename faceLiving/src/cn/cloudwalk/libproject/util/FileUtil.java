package cn.cloudwalk.libproject.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
    public static final String TAG = "FileUtil";
    public static final File parentPath = Environment.getExternalStorageDirectory();
    public static String storagePath = "";
    public static final String DST_FOLDER_NAME = Util.AppName;

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];

                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();

            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    // facePhotoStr=new
    // StringBuilder(parentPath.getAbsolutePath())
    // .append(File.separator).append(DST_FOLDER_NAME).append(File.separator).toString();
    public static byte[] file2byte(File file) throws IOException {
        byte[] bytes = null;
        if (file != null) {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if (length > Integer.MAX_VALUE) {// 当文件的长度超过了int的最大值
                System.out.println("this file is max ");
                is.close();
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();
            // 如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length) {
                System.out.println("file length is error");
                return null;
            }
        }
        return bytes;
    }

    public static void mkParentDir(String path) {
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath
     */
    public static void mkDir(String dirPath) {
        String[] dirArray = dirPath.split("/");
        String pathTemp = "";
        for (int i = 1; i < dirArray.length; i++) {
            pathTemp = pathTemp + "/" + dirArray[i];
            File newF = new File(dirArray[0] + pathTemp);
            if (!newF.exists()) {
                newF.mkdir();
            }
        }
    }

    public static void createModelFile(String modelDirPath, String modelFileName, AssetManager assetManager) {
        String content = null;
        String parentDir = "model";
        List<String> fileNameList = null;
        String[] files = null;

        String fileAbsPath = "";
        if (modelDirPath.endsWith("/")) {
            fileAbsPath = modelDirPath + modelFileName;
        } else {
            fileAbsPath = modelDirPath + "/" + modelFileName;
        }

        File modelFile = new File(fileAbsPath);
        if (modelFile.exists()) {
            return;
        }
        mkDir(modelDirPath);

        try {
            files = assetManager.list(parentDir);
            fileNameList = Arrays.asList(files);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        for (String fileName : fileNameList) {
            Log.e(TAG, fileName);
            content = readRawFileToString(parentDir + File.separator + fileName, assetManager);
            writeStringToFile(content, fileAbsPath);
        }
    }

    public static void createModelFileAll(String modelDirPath, AssetManager assetManager) {
        String parentDir = "model";
        List<String> fileNameList = null;
        String[] files = null;

        String fileAbsPath = "";
        if (modelDirPath.endsWith("/")) {
            fileAbsPath = modelDirPath;
        } else {
            fileAbsPath = modelDirPath + "/";
        }

        mkDir(modelDirPath);
        try {
            files = assetManager.list(parentDir);
            fileNameList = Arrays.asList(files);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        for (String fileName : fileNameList) {
            Log.e(TAG, fileName);
            File modelFile = new File(fileAbsPath + fileName);
            if (!modelFile.exists()) {
                copyRawFileToSdcard(parentDir + File.separator + fileName, assetManager, fileAbsPath + fileName);
            }
        }
    }

    public static String readRawFileToString(String rawFileName, AssetManager assetManager) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(rawFileName);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        if (inputStream != null)
            return inputStreamToString(inputStream);
        return null;
    }

    public static byte[] readRawFileToByteArray(String rawFileName, AssetManager assetManager) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(rawFileName);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        if (inputStream != null)
            return inputStreamToByteArray(inputStream);
        return null;
    }

    public static void copyRawFileToSdcard(String rawFileName, AssetManager assetManager, String outPutFileAbs) {
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(rawFileName);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        if (inputStream != null)
            inputStreamToFile(inputStream, outPutFileAbs);
    }

    public static String inputStreamToString(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static void inputStreamToFile(InputStream inputStream, String absPath) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            byte[] imgBytes = outputStream.toByteArray();
            FileOutputStream fos = new FileOutputStream(absPath, false);
            fos.write(imgBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStringToFile(String content, String file_name) {
        try {
            File file = new File(file_name);

            FileOutputStream fileW = new FileOutputStream(file.getCanonicalPath());
            fileW.write(content.getBytes());
            fileW.close();

        } catch (Exception e) {
            //
        }
    }

    public static void writeByteArrayToFile(byte[] content, String file_name) {
        try {

            File file = new File(file_name);

            FileOutputStream fileW = new FileOutputStream(file.getCanonicalPath());
            fileW.write(content);
            fileW.close();

        } catch (Exception e) {
            //
        }
    }

    public static void writeByteArrayToSD(byte[] content, String file_name) {
        try {

            File parentPath = Environment.getExternalStorageDirectory();
            String file_path = parentPath.getAbsolutePath() + ("/") + DST_FOLDER_NAME + ("/") + file_name;

            File file = new File(file_path);

            FileOutputStream fileW = new FileOutputStream(file.getCanonicalPath());
            fileW.write(content);
            fileW.close();

        } catch (Exception e) {
            //
        }
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            //
        }
    }

    /**
     * 拷贝assets下文件
     *
     * @param context
     * @param origin
     * @param destination
     * @throws IOException
     */
    public static void assetsDataToDest(Context context, String origin, String destination) throws IOException {
        InputStream input = context.getAssets().open(origin);
        OutputStream output = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length = input.read(buffer);
        while (length > 0) {
            output.write(buffer, 0, length);
            length = input.read(buffer);
        }
        output.flush();
        input.close();
        output.close();
    }
//
//    /**
//     * 解压缩文件
//     *
//     * @param zipFileString
//     * @param outPathString
//     * @throws Exception
//     */
//    public static void unZipFolder(String zipFileString, String outPathString) throws Exception {
//        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
//        ZipEntry zipEntry;
//        String szName = "";
//        while ((zipEntry = inZip.getNextEntry()) != null) {
//            szName = zipEntry.getName();
//            if (szName.contains("../")) {
//                continue;
//            }
//            if (zipEntry.isDirectory()) {
//                szName = szName.substring(0, szName.length() - 1);
//                File folder = new File(outPathString + File.separator + szName);
//                folder.mkdirs();
//            } else {
//                File file = new File(outPathString + File.separator + szName);
//                file.createNewFile();
//                FileOutputStream out = new FileOutputStream(file);
//                int len;
//                byte[] buffer = new byte[1024];
//                while ((len = inZip.read(buffer)) != -1) {
//                    out.write(buffer, 0, len);
//                    out.flush();
//                }
//                out.close();
//            }
//        }
//        inZip.close();
//    }

    /**
     * 解压缩模型文件
     *
     * @param zipFileString
     * @param outPathString
     * @throws Exception
     */
    public static void unZipModelFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (szName.contains("../")) {//避免Android中ZipEntry漏洞,详情参考http://www.520monkey.com/archives/833
                continue;
            }
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                //通过过滤文件后缀名,确保只解压了模型文件
                if (getExtensionNameWithDot(szName).equals(".bin") || getExtensionNameWithDot(szName).equals(".mdl") || getExtensionNameWithDot(szName).equals(".txt")) {
                    File file = new File(outPathString + File.separator + szName);
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = inZip.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                } else {
                    continue;
                }
            }
        }
        inZip.close();
    }

    public static String getExtensionNameWithDot(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }
}
