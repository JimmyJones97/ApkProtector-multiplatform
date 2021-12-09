package com.secure.dex.utils;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = is.read(buffer)) > 0)
            bos.write(buffer, 0, len);
        is.close();
        return bos.toByteArray();
    }

    public static void writeString(File file, String str) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        try {
            out.write(str);
        } catch (IOException e) {
            System.out.println("Exception " + e);
        } finally {
            out.close();
        }
    }

    public static List<File> getFiles(File[] files) {
        List<File> list = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(getFiles(file.listFiles()));
            } else {
                list.add(file);
            }
        }
        return list;
    }

    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    delete(f);
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    public static String getWorkPath() {
        return System.getProperty("user.dir");
    }

    public static boolean copyFile(String src, String dest) {
        try {
            Files.copy(new File(src).toPath(), new File(dest).toPath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void writeInt(byte @NotNull [] data, int off, int value) {
        data[off++] = (byte) (value & 0xFF);
        data[off++] = (byte) ((value >>> 8) & 0xFF);
        data[off++] = (byte) ((value >>> 16) & 0xFF);
        data[off] = (byte) ((value >>> 24) & 0xFF);
    }

    public static int readInt(byte @NotNull [] data, int off) {
        return data[off + 3] << 24 | (data[off + 2] & 0xFF) << 16 | (data[off + 1] & 0xFF) << 8
                | data[off] & 0xFF;
    }

    public static boolean isExists(String path) {
        if (path == null) {
            return false;
        }
        File file = new File(path);
        return file != null && file.exists();
    }

    public static void writeFile(String path, String content, boolean append) {
        try {
            File f = new File(path);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            if (!f.exists()) {
                f.createNewFile();
                f = new File(path);
            }

            FileWriter fw = new FileWriter(f, append);
            if (content != null && !"".equals(content)) {
                fw.write(content);
                fw.flush();
            }

            fw.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public static void createDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }

    }

    public static void copyFolder(File source, File destination) {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = null;
            OutputStream out = null;

            try {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (Exception e) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static boolean copy(String srcFile, String destFile) {
        FileInputStream in = null;
        FileOutputStream out = null;

        boolean var5;
        try {

            if (!isExists(destFile)) {
                File dst = new File(destFile);
                dst.getParentFile().mkdirs();
                dst.createNewFile();
            }

            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];

            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }

            out.flush();
            boolean var6 = true;
            return var6;
        } catch (Exception var20) {
            System.out.println("Error!" + var20);
            var5 = false;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

            if (null != out) {
                try {
                    out.close();
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

        }
        return var5;
    }

    public static String readFileContent(String path) {
        StringBuffer sb = new StringBuffer();
        if (!isExists(path)) {
            return sb.toString();
        } else {
            FileInputStream ins = null;

            try {
                ins = new FileInputStream(new File(path));
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            } finally {
                if (ins != null) {
                    try {
                        ins.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }
                }

            }
            return sb.toString();
        }
    }
}
