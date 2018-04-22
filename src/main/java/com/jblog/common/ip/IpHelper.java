package com.jblog.common.ip;

import com.jblog.util.FileUtil;
import com.jblog.util.PoiUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpHelper {
    private static IpTree ipTree = IpTree.getInstance();

    private static final String ipFile = "IPSearch/ipDatabase.csv";

    private static final String regionFile = "IPSearch/ipRegion.xlsx";

    static{
        buildTrain();
    }

    private static void buildTrain() {

        List<IpRelation> ipRelationList;
        try {
            ipRelationList = IpHelper.getIpRelation();
            int count = 0;
            for (IpRelation ipRelation : ipRelationList) {
                ipTree.train(ipRelation.getIpStart(), ipRelation.getIpEnd(), ipRelation.getProvince());
                if(count > 10){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**

     * 静态方法，传入ip地址，返回ip地址所在城市或地区

     * @param ip    IP地址，例：58.30.15.255

     * @return  返回IP地址所在城市或地区，例：北京市

     */

    public static String findRegionByIp(String ip){

        String loc = ipTree.findIp(ip);
        return StringUtils.isEmpty(loc) ? "未知网友" :  loc + "网友";

    }

    public static List<IpRelation> getIpRelation() throws Exception {
        // <ipCode, province>
        Map<Integer, String> regionRelationMap = getRegionRelationMap();
        String file =  IpHelper.class.getClassLoader().getResource(ipFile).getFile();
        BufferedReader ipRelationReader = FileUtil.readFile(file);

        String line;
        List<IpRelation> list = new ArrayList<IpRelation>();

        while((line = ipRelationReader.readLine()) != null){
            String[] split = line.split(",");
            String ipStart = split[0];
            String ipEnd = split[1];
            Integer ipCode = Integer.valueOf(split[2]);

            String province = regionRelationMap.get(ipCode);
            IpRelation ipRelation = new IpRelation();
            ipRelation.setIpStart(ipStart);
            ipRelation.setIpEnd(ipEnd);
            ipRelation.setProvince(province);
            list.add(ipRelation);
        }
        return list;
    }


    /**
     * @return Map<ipCode, province>
     * @throws Exception
     */
    public static Map<Integer, String> getRegionRelationMap() throws Exception {

        String file =  IpHelper.class.getClassLoader().getResource(regionFile).getFile();
        Workbook workbook = PoiUtil.getWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        int rowLen = sheet.getPhysicalNumberOfRows();
        Map<Integer, String> map = new HashMap<>(rowLen - 1);
        for (int i = 1; i < rowLen; i++) {
            Row row = sheet.getRow(i);
            String province = row.getCell(0).getStringCellValue();
            Double a = row.getCell(2).getNumericCellValue();
            Integer ipCode = a.intValue();
            map.put(ipCode, province);
        }
        return map;
    }
}
