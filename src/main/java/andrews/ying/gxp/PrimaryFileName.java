package andrews.ying.gxp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PrimaryFileName {
    private final static String[] localPrimaryFilesInSolr = {
            "file:///",
            "file:///C:/CSUFtCollins.mpeg",
            "file:///C:/Program%20Files/GXP%20Xplorer%20Platform%20Data/data/managed/managed/CSU%20FtCollins%20short.mpeg",
            "file:///C:/Program%20Files/GXP%20Xplorer%20Platform%20Data/data/managed/managed/A&M%20video.mpeg",
            "file:///C:/Program%20Files/GXP%20Xplorer%20Platform%20Data/data/managed/managed/CSU+FtCollins%20(1).mpeg",
            "file:///C:/Program%20Files/GXP%20Xplorer%20Platform%20Data/data/managed/managed/Hangul%20아름다운사.mpeg"
    };

    private final static String[] networkPrimaryFilesInSolr = {
            "file:////ncdm-nas/U/Datasets/powerslice/CSU_3_min%20-%20Copy.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/KevinVideos/Kevin_CSU_3_min.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/KevinVideos/CSU_FtCollins.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/CSU_9_min.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/MQ9/MinotaurMaven_MQ9_clip3.ts",
            "file:////ncdm-nas/U/Datasets/powerslice/CSU_3_min.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/MQ9/4.ts",
            "file:////ncdm-nas/U/Datasets/powerslice/CSU_9_min.mpeg",
            "file:////ncdm-nas/U/Datasets/powerslice/KevinVideos/MinotaurMaven_MQ9_clip3.ts",
            "file:////ncdm-nas/U/Datasets/powerslice/GEL%20Videos/160629_DI_I.ts"
    };


    public static void main(String[] args) throws IOException, URISyntaxException {
        for (String s : localPrimaryFilesInSolr) {
            System.out.println("===============================");
//            System.out.println("Current way: " + currentWay(s));
            System.out.println("                  New way: " + newWay(s));
//            System.out.println("TASS way: " + TassWay(s));
//            System.out.println("BrowserManager way:  " + BrowseManagerWay(s));
            System.out.println("ReadablePathCalcuator way: " + ReadablePathCalculatorWay(s));
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        for (String s : networkPrimaryFilesInSolr) {
            System.out.println("===============================");
//            System.out.println("Current way: " + currentWay(s));
            System.out.println("                  New way: " + newWay(s));
//            System.out.println("TASS way: " + TassWay(s));
//            System.out.println("BrowserManager way: " + BrowseManagerWay(s));
            System.out.println("ReadablePathCalcuator way: " + ReadablePathCalculatorWay(s));
        }
    }

    public static String newWay(String filePath) {
        String normalizedPath = filePath;
        if (filePath.startsWith("file")) {
            try {
                URI uriPath = new URI(filePath);
                normalizedPath = uriPath.getRawPath();

                if (normalizedPath.length() > 2) {
                    boolean isLocalWindows = normalizedPath.charAt(0) == '/' && normalizedPath.charAt(1) != '/' && normalizedPath.charAt(2) == ':';

                    normalizedPath = normalizedPath.replace('/', '\\');
                    normalizedPath = normalizedPath.replace("%20", " ");
                    normalizedPath = normalizedPath.replace("+", "%2B");

                    if (isLocalWindows && normalizedPath.charAt(0) == '\\' && normalizedPath.charAt(1) != '\\' )
                        normalizedPath = normalizedPath.substring(1);
                }

            } catch (URISyntaxException ex) {
                System.out.println(String.format("Could not generate primary file URI: %s", filePath));
            }
        }
        return normalizedPath;
    }

    public static String currentWay(String s) {
        return s.replaceAll("/", "\\\\").replaceFirst("^file:[\\\\]*", "\\\\\\\\");
    }

    public static String TassWay(String s) {
        s = s.replaceFirst("^file:([\\\\/]*)", "");
        File f = new File(s);
        String result = "NO RESULT";
        try {
            result = f.toPath().toRealPath().toString();
        } catch (IOException e) {
            System.out.println("Failed in TASS way");
        }
        return result;
    }

    public static String BrowseManagerWay(String s) throws URISyntaxException {
        if (s.startsWith("file:/")) {
            String result = null;
            URI primaryFileURI = new URI(s);
            result = primaryFileURI.getPath();
            return result;
        } else {
            return s;
        }
    }

    public static String ReadablePathCalculatorWay(String primaryFile) {
        String path = null;

        if( primaryFile.startsWith("file") )
        {
            try
            {
                URI uriPath = new URI(primaryFile);
                if (uriPath != null)
                {
                    path = uriPath.getRawPath();
                    if( path.length() > 2 )
                    {
                        boolean isLinux = path.charAt(0) == '/' && path.charAt(1) != '/' && path.charAt(2) != ':';
                        if (!isLinux)
                        {
                            path = path.replace('/', '\\');
                        }
                        path = path.replace("%20", " ");
                        path = path.replace("+", "%2B");

                        if( !isLinux && path.charAt(0) == '\\' && path.charAt(1) != '\\' )
                        {
                            path = path.substring(1);
                        }
                    }

                }
            }
            catch (URISyntaxException ex)
            {
                System.out.println("Could not generate primary file URI: "+primaryFile);
            }
        }
        else if(primaryFile.startsWith("s3"))
        {
            path = primaryFile.replace("%20", " ");
            path = path.replace("+", "%2B");
        }
        else
        {
            path = primaryFile;
        }
        return path;

    }
}
