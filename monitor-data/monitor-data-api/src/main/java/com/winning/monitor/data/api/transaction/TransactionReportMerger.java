package com.winning.monitor.data.api.transaction;


import com.winning.monitor.data.api.transaction.vo.TransactionMachineVO;
import com.winning.monitor.data.api.transaction.vo.TransactionNameVO;
import com.winning.monitor.data.api.transaction.vo.TransactionReportVO;
import com.winning.monitor.data.api.transaction.vo.TransactionTypeVO;
import com.winning.monitor.data.api.vo.AllDuration;
import com.winning.monitor.data.api.vo.Duration;
import com.winning.monitor.data.api.vo.Range;
import com.winning.monitor.data.api.vo.Range2;

public class TransactionReportMerger {

    public static void mergeReport(TransactionReportVO old, TransactionReportVO other) {
        old.getDomainNames().addAll(old.getDomainNames());
        old.getIps().addAll(old.getIps());

        for (TransactionMachineVO otherMachine : other.getMachines()) {
            TransactionMachineVO oldMachine = old.getTransactionMachine(otherMachine.getIp());

            //如果源Report不存在Machine
            if (oldMachine == null) {
                old.addTransactionMachine(otherMachine);
            } else {
                for (TransactionTypeVO otherType : otherMachine.getTransactionTypes()) {
                    TransactionTypeVO oldType = oldMachine.getTransactionType(otherType.getId());
                    if (oldType == null) {
                        oldMachine.addTransactionType(otherType);
                    }
                    //如果存在相同的IP,相同的TransactionType
                    else {
                        //进行合并
                        mergeType(oldType, otherType);
                        for (TransactionNameVO otherName : otherType.getTransactionNames()) {
                            TransactionNameVO oldName = oldType.getTransactionName(otherName.getId());
                            if (oldName == null)
                                oldType.addTransactionName(otherName);
                            else
                                mergeName(oldName, otherName);
                        }
                    }
                }
            }
        }
    }


    public static void mergeName(TransactionNameVO old, TransactionNameVO other) {
        long totalCountSum = old.getTotalCount() + other.getTotalCount();
        if (totalCountSum > 0) {
            double line95Values = old.getLine95Value() * old.getTotalCount() + other.getLine95Value()
                    * other.getTotalCount();
            double line99Values = old.getLine99Value() * old.getTotalCount() + other.getLine99Value()
                    * other.getTotalCount();

            old.setLine95Value(line95Values / totalCountSum);
            old.setLine99Value(line99Values / totalCountSum);
        }

        old.setTotalCount(totalCountSum);
        old.setFailCount(old.getFailCount() + other.getFailCount());
        old.setTps(old.getTps() + other.getTps());

        if (other.getMin() < old.getMin()) {
            old.setMin(other.getMin());
        }

        if (other.getMax() > old.getMax()) {
            old.setMax(other.getMax());
        }

        old.setSum(old.getSum() + other.getSum());
        old.setSum2(old.getSum2() + other.getSum2());

        if (old.getTotalCount() > 0) {
            old.setFailPercent(old.getFailCount() * 100.0 / old.getTotalCount());
            old.setAvg(old.getSum() / old.getTotalCount());
            old.setStd(std(old.getTotalCount(), old.getAvg(), old.getSum2(), old.getMax()));
        }

        for (Range otherRange : other.getRanges().values()) {
            Range oldRange = old.getRanges().get(otherRange.getValue());
            if (oldRange == null)
                old.getRanges().put(otherRange.getValue(), otherRange);
            else
                mergeRange(oldRange, oldRange);
        }

        for (AllDuration otherDuration : other.getAllDurations().values()) {
            AllDuration oldDuration = old.getAllDurations().get(otherDuration.getValue());
            if (oldDuration == null)
                old.getAllDurations().put(otherDuration.getValue(), otherDuration);
            else
                mergeAllDuration(oldDuration, otherDuration);
        }

        for (Duration otherDuration : other.getDurations().values()) {
            Duration oldDuration = old.getDurations().get(otherDuration.getValue());
            if (oldDuration == null)
                old.getDurations().put(otherDuration.getValue(), otherDuration);
            else
                mergeDuration(oldDuration, otherDuration);
        }
    }

    public static void mergeRange(Range old, Range range) {
        old.setCount(old.getCount() + range.getCount());
        old.setFails(old.getFails() + range.getFails());
        old.setSum(old.getSum() + range.getSum());

        if (old.getCount() > 0) {
            old.setAvg(old.getSum() / old.getCount());
        }
    }

    public static void mergeRange2(Range2 old, Range2 range) {
        old.setCount(old.getCount() + range.getCount());
        old.setFails(old.getFails() + range.getFails());
        old.setSum(old.getSum() + range.getSum());

        if (old.getCount() > 0) {
            old.setAvg(old.getSum() / old.getCount());
        }
    }

    public static void mergeDuration(Duration old, Duration duration) {
        old.setCount(old.getCount() + duration.getCount());
        old.setValue(duration.getValue());
    }

    public static void mergeAllDuration(AllDuration old, AllDuration duration) {
        old.setCount(old.getCount() + duration.getCount());
        old.setValue(duration.getValue());
    }

    public static void mergeType(TransactionTypeVO old, TransactionTypeVO other) {
        long totalCountSum = old.getTotalCount() + other.getTotalCount();
        if (totalCountSum > 0) {
            double line95Values = old.getLine95Value() * old.getTotalCount() + other.getLine95Value()
                    * other.getTotalCount();
            double line99Values = old.getLine99Value() * old.getTotalCount() + other.getLine99Value()
                    * other.getTotalCount();

            old.setLine95Value(line95Values / totalCountSum);
            old.setLine99Value(line99Values / totalCountSum);
        }

        old.setTotalCount(totalCountSum);
        old.setFailCount(old.getFailCount() + other.getFailCount());
        old.setTps(old.getTps() + other.getTps());

        if (other.getMin() < old.getMin()) {
            old.setMin(other.getMin());
        }

        if (other.getMax() > old.getMax()) {
            old.setMax(other.getMax());
        }

        old.setSum(old.getSum() + other.getSum());
        old.setSum2(old.getSum2() + other.getSum2());

        if (old.getTotalCount() > 0) {
            old.setFailPercent(old.getFailCount() * 100.0 / old.getTotalCount());
            old.setAvg(old.getSum() / old.getTotalCount());
            old.setStd(std(old.getTotalCount(), old.getAvg(), old.getSum2(), old.getMax()));
        }

        for (Range2 otherRange2 : other.getRange2s().values()) {
            Range2 oldRange2 = old.getRange2s().get(otherRange2.getValue());
            if (oldRange2 == null)
                old.getRange2s().put(otherRange2.getValue(), otherRange2);
            else
                mergeRange2(oldRange2, oldRange2);
        }

        for (AllDuration otherDuration : other.getAllDurations().values()) {
            AllDuration oldDuration = old.getAllDurations().get(otherDuration.getValue());
            if (oldDuration == null)
                old.getAllDurations().put(otherDuration.getValue(), otherDuration);
            else
                mergeAllDuration(oldDuration, otherDuration);
        }
    }

    static double std(long count, double avg, double sum2, double max) {
        double value = sum2 / count - avg * avg;

        if (value <= 0 || count <= 1) {
            return 0;
        } else if (count == 2) {
            return max - avg;
        } else {
            return Math.sqrt(value);
        }
    }

}
