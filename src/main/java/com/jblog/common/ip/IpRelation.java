package com.jblog.common.ip;

import lombok.Getter;
import lombok.Setter;

public class IpRelation {

    @Setter @Getter private String ipStart;

    @Setter @Getter private String ipEnd;

    @Setter @Getter private int ipCode;

    @Setter @Getter private String province;

    @Setter @Getter private String city;
}
