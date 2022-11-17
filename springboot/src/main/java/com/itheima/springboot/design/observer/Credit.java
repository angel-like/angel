package com.itheima.springboot.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 小明借款 (从张三王五 借钱进来)
 */
public interface Credit {
    void borrow(Dredit dredit);
    void setState(int state);
    void nofifyDredit();
}

class XiaoMing implements Credit {
    private List<Dredit> dredits = new ArrayList<>();
    private Integer stat = 0;//1代表有钱

    /**
     * 小明去找 贷款方借钱 （每借一个，这边记录一个）
     * @param dredit
     */
    @Override
    public void borrow(Dredit dredit) {
        dredits.add(dredit);
    }

    /**
     * 如果小明有钱了， 即状态改为1
     * 就去通知还钱
     * @param stat
     */
    @Override
    public void setState(int stat) {
        this.stat=stat;
        if(stat==1){
            nofifyDredit();
        }
    }


    /**
     * 小明有钱了就去 还款
     * 这边通知所有记录的 dredits 贷款方来拿钱
     */
    @Override
    public void nofifyDredit() {
        dredits.forEach(e -> e.takeMoney());
    }
}
