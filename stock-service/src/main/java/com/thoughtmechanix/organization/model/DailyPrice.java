package com.thoughtmechanix.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dailyprice")
public class DailyPrice {
    /*ID VARCHAR(100)PRIMARY KEY NOT NULL,
    SYMBOL VARCHAR(100),
    SERIES VARCHAR(100) ,
    OPEN NUMERIC(10,2),
    HIGH NUMERIC(10,2),
    LOW NUMERIC(10,2),
    CLOSE NUMERIC(10,2),
    LAST NUMERIC(10,2),
    PREVCLOSE NUMERIC(10,2),
    TOTTRDQTY bigint,
    TOTTRDVAL bigint,
    TIMESTAMP VARCHAR(100),
    TOTALTRADES bigint,
    ISIN VARCHAR(100));
    numeric(p,s)*/
    @Id
    @Column(name = "id", nullable = false)
    String id;
    @Column(name = "SYMBOL")
    private String symbol;
    @Column(name = "SERIES")
    private String series;
    @Column(name = "OPEN")
    private BigDecimal open;
    @Column(name = "HIGH")
    private BigDecimal high;
    @Column(name = "LOW")
    private BigDecimal low;
    @Column(name = "CLOSE")
    private BigDecimal close;
    @Column(name = "LAST")
    private BigDecimal last;
    @Column(name = "PREVCLOSE")
    private BigDecimal prevClose;
    @Column(name = "TOTTRDQTY")
    private Integer totalTradedQuantity;
    @Column(name = "TOTTRDVAL")
    private BigDecimal totalTradedValues;
    @Column(name = "TIMESTAMP")
    private String timestamp;
    @Column(name = "TOTALTRADES")
    private Integer totalTrades;
    @Column(name = "ISIN")
    private String isin;

    public DailyPrice(String[] recordFields) {
        this.id = UUID.randomUUID().toString();
        this.symbol = recordFields[0];
        this.series = recordFields[1];
        this.open = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[2])));
        this.high = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[3])));
        this.low = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[4])));
        this.close = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[5])));
        this.last = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[6])));
        this.prevClose = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[7])));
        this.totalTradedQuantity = Integer.valueOf(checkNull(recordFields[8]));
        this.totalTradedValues = BigDecimal.valueOf(Double.valueOf(checkNull(recordFields[9])));
        ;
        this.timestamp = recordFields[10];
        this.totalTrades = Integer.valueOf(checkNull(recordFields[11]));
        this.isin = recordFields[12];

    }
    public DailyPrice ()
    {
        
    }
    private String checkNull(String value) {
        return value != null ? value : "0";
    }

    public String toString() {
        return symbol + "," + series + ", " + open + ",";
    }
}
