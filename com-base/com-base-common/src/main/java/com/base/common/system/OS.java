package com.base.common.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * An operating system associated with servers and images.
 * </p>
 * 
 * @author Administrator、Dr.Deng
 * @version 2010-11-27
 */
public enum OS {

    /**
     * Generic UNIX
     */
    UNIX,
    /**
     * Ubuntu
     */
    UBUNTU,
    /**
     * Debian
     */
    DEBIAN,
    /**
     * Solaris
     */
    SOLARIS,
    /**
     * Fedora Core
     */
    FEDORA_CORE,
    /**
     * RHEL
     */
    RHEL,
    /**
     * FreeBSD
     */
    FREE_BSD,
    /**
     * OpenBSD
     */
    OPEN_BSD,
    /**
     * CentOS
     */
    CENT_OS,
    /**
     * Generic Windows
     */
    WINDOWS,
    /**
     * No clue
     */
    UNKNOWN;

    static public OS guess(String name) {

        if (name == null) {
            return UNKNOWN;
        }
        name = name.toLowerCase();
        if (name.indexOf("centos") > -1) {
            return CENT_OS;
        } else if (name.indexOf("ubuntu") > -1) {
            return UBUNTU;
        } else if (name.indexOf("fedora") > -1) {
            return FEDORA_CORE;
        } else if (name.indexOf("windows") > -1) {
            return WINDOWS;
        } else if (name.indexOf("red hat") > -1 || name.indexOf("redhat") > -1 || name.indexOf("red-hat") > -1
                || name.indexOf("rhel") > -1) {
            return RHEL;
        } else if (name.indexOf("debian") > -1) {
            return DEBIAN;
        } else if (name.indexOf("bsd") > -1) {
            if (name.indexOf("free") > -1) {
                return FREE_BSD;
            } else if (name.indexOf("open") > -1) {
                return OPEN_BSD;
            } else {
                return UNIX;
            }
        } else if (name.indexOf("solaris") > -1) {
            return SOLARIS;
        }
        return OS.UNKNOWN;
    }

    /**
     * Provides an appropriate device ID (e.g. sdh) for this platform given a
     * device letter.
     * 
     * @param letter
     *            the letter to be mapped into a platform-specific device ID
     * @return the platform-specific device ID for the target letter
     */
    public String getDeviceId(String letter) {
        switch (this) {
        case WINDOWS:
            return "xvd" + letter;
        default:
            return "sd" + letter;
        }
    }

    /**
     * Provides a device mapping (e.g. /dev/sdh) for the target device letter.
     * 
     * @param letter
     *            the letter to be mapped
     * @return the device mapping for the specified letter
     */
    public String getDeviceMapping(String letter) {
        switch (this) {
        case WINDOWS:
            return "xvd" + letter;
        default:
            return "/dev/sd" + letter;
        }
    }

    public boolean isBsd() {
        return (equals(FREE_BSD) || equals(OPEN_BSD));
    }

    public boolean isLinux() {
        switch (this) {
        case SOLARIS:
        case FREE_BSD:
        case OPEN_BSD:
        case WINDOWS:
        case UNKNOWN:
            return false;
        default:
            return true;
        }
    }

    public boolean isOpen() {
        return (isLinux() || isBsd() || equals(SOLARIS));
    }

    public boolean isUnix() {
        return (!isWindows() && !equals(UNKNOWN));
    }

    public boolean isWindows() {
        return equals(WINDOWS);
    }

    public static List<String> getIpAddress() {
        List<String> adds = new ArrayList<String>();
        String osName = System.getProperty("os.name");
        String command = null;
        Process p = null;
        BufferedReader br = null;
        if (osName == null) {
            return null;
        } else {
            if (osName.startsWith("Windows")) {
                command = "ipconfig /all";// 定义系统命令

                try {
                    p = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 执行命令

                String line = null;
                try {
                    br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
                    while ((line = br.readLine()) != null) // 解析地址
                    {
                        // 用正则表达式拿IP地址
                        if (line.indexOf("IP Address") > 0 || line.indexOf("IPv4 地址") > 0
                                || line.indexOf("IPv4 Address") > 0) {
                            Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                            Matcher match = pattern.matcher(line);
                            if (match.find()) {
                                adds.add(match.group());
                            }
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!adds.contains("127.0.0.1")) {
                    adds.add("127.0.0.1");
                }
                return adds;

            } else {
                command = "ifconfig";
                try {
                    p = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        if (line.indexOf("inet addr") != -1 || line.indexOf("inet 地址") != -1) {
                            String[] ads = line.trim().split("  ");
                            String ad = ads[0];
                            adds.add(ad.substring(ad.indexOf(":") + 1));
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!adds.contains("127.0.0.1")) {
                    adds.add("127.0.0.1");
                }
                return adds;
            }
        }
    }

    public String toString() {
        switch (this) {
        case UNIX:
            return "Generic Unix";
        case UBUNTU:
            return "Ubuntu";
        case DEBIAN:
            return "Debian";
        case SOLARIS:
            return "Solaris";
        case FEDORA_CORE:
            return "Fedora";
        case RHEL:
            return "Red Hat";
        case FREE_BSD:
            return "FreeBSD";
        case OPEN_BSD:
            return "OpenBSD";
        case CENT_OS:
            return "CentOS";
        case WINDOWS:
            return "Windows";
        }
        return "Unknown";
    }

    /**
     * 获得本机IP地址
     * 
     * @return
     */
    public static String getLocalhostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {

        }

        return "";
    }
}
