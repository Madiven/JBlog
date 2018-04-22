package com.jblog.common.ip;

public class IpTree {

    private static IpTree instance = null;
    private IpNode rootNode = new IpNode();
    private final String NO_ADDRESS = "未知";

    private IpTree() {
    }

    public static IpTree getInstance() {
        if (instance == null) {
            instance = new IpTree();
        }
        return instance;
    }

    public void train(String ipStart, String ipEnd, String addressCode) {

        int ip_s = ipToInt(ipStart);
        int ip_e = ipToInt(ipEnd);

        if (ip_e == -1 || ip_s == -1) {
            return;
        }

        IpNode curNode = rootNode;
        IpNode leftNode = null;
        IpNode rightNode = null;
        boolean flag = false;
        for (int i = 0; i < 32; i++) {
            int ip_s_bit = (0x80000000 & ip_s) >>> 31;
            int ip_e_bit = (0x80000000 & ip_e) >>> 31;

            if(flag == false) {
                if ((ip_s_bit ^ ip_e_bit) == 0) {
                    if (ip_s_bit == 1) {
                        if (curNode.rightNode == null) {
                            curNode.rightNode = new IpNode();
                        }
                        curNode = curNode.rightNode;
                    }
                    else {
                        if (curNode.leftNode == null) {
                            curNode.leftNode = new IpNode();
                        }
                        curNode = curNode.leftNode;
                    }
                    if(i == 31) {
                        curNode.addressCode = addressCode;
                    }
                }
                else {
                    flag = true;
                    if(curNode.leftNode == null) {
                        curNode.leftNode = new IpNode();
                    }
                    leftNode = curNode.leftNode;
                    if(curNode.rightNode == null) {
                        curNode.rightNode = new IpNode();
                    }
                    rightNode = curNode.rightNode;
                    if(i == 31){
                        leftNode.addressCode = addressCode;
                        rightNode.addressCode = addressCode;
                    }
                }
            } else {
                if (ip_s_bit == 1) {
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode = leftNode.rightNode;
                } else {
                    if (leftNode.leftNode == null) {
                        leftNode.leftNode = new IpNode();
                    }
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode.rightNode.addressCode = addressCode;
                    leftNode = leftNode.leftNode;
                }
                if(i == 31) {
                    leftNode.addressCode = addressCode;
                }
                if (ip_e_bit == 1) {
                    if (rightNode.rightNode == null) {
                        rightNode.rightNode = new IpNode();
                    }

                    if(rightNode.leftNode == null){
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode.leftNode.addressCode = addressCode;
                    rightNode = rightNode.rightNode;

                } else {
                    if (rightNode.leftNode == null) {
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode = rightNode.leftNode;
                }

                if(i == 31) {
                    rightNode.addressCode = addressCode;
                }
            }
            ip_s = ip_s << 1;
            ip_e = ip_e << 1;
        }
    }



    public String findIp(String ip) {

        IpNode curNode = rootNode;
        int ip_int = ipToInt(ip);

        if (ip_int == -1) {
            return NO_ADDRESS;
        }

        for (int i = 0; i < 32; i++) {

            int ip_s_bit = (0x80000000 & ip_int) >>> 31;

            if (ip_s_bit == 0) {
                curNode = curNode.leftNode;
            } else {
                curNode = curNode.rightNode;
            }

            if (curNode == null) {
                return NO_ADDRESS;
            }

            if (curNode.addressCode != null && !curNode.addressCode.trim().equals("")){
                return curNode.addressCode;
            }
            ip_int = ip_int << 1;
        }

        return NO_ADDRESS;
    }



    private int ipToInt(String strIP) {

        try {
            int[] ip = new int[4];
            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);

            ip[0] = Integer.parseInt(strIP.substring(0, position1));
            ip[1] = Integer.parseInt(strIP.substring(position1 + 1, position2));
            ip[2] = Integer.parseInt(strIP.substring(position2 + 1, position3));
            ip[3] = Integer.parseInt(strIP.substring(position3 + 1));

            int ip_int = (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
            return ip_int;

        } catch (Exception e) {
            return -1;
        }
    }

    private class IpNode {

        private IpNode leftNode;

        private IpNode rightNode;

        private String addressCode;
    }

    private void loopTree(IpNode ipNode, int depth){
        System.out.println(depth + "\t" + ipNode.addressCode);

        if(ipNode.leftNode != null) {
            System.out.println("left");
            loopTree(ipNode.leftNode, depth + 1);
        }

        if(ipNode.rightNode != null) {
            System.out.println("right");

            loopTree(ipNode.rightNode, depth + 1);
        }
    }
}