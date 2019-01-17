package com.example.changeloglibrary.internal;

import java.util.LinkedList;

public class ChangeLog {

    /**
     * All changelog rows
     */
    private LinkedList<ChangeLogRow> rows;

    /**
     * Use a bulleted List
     */
    private boolean bulletedList;

    //-----------------------------------------------------------------------

    public ChangeLog(){
        rows=new LinkedList<ChangeLogRow>();
    }

    /**
     * Add new {@link ChangeLogRow} to rows
     *
     * @param row
     */
    public void addRow(ChangeLogRow row){
        if (row!=null){
            if (rows==null) rows=new LinkedList<ChangeLogRow>();
            rows.add(row);
        }
    }

    /**
     * Clear all rows
     */
    public void clearAllRows(){
        rows=new LinkedList<ChangeLogRow>();
    }


    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("bulletedList="+bulletedList);
        sb.append("\n");
        if (rows!=null){
            for (ChangeLogRow row:rows){
                sb.append("row=[");
                sb.append(row.toString());
                sb.append("]\n");
            }
        }else{
            sb.append("rows:none");
        }
        return sb.toString();
    }

    //-----------------------------------------------------------------------

    public boolean isBulletedList() {
        return bulletedList;
    }

    public void setBulletedList(boolean bulletedList) {
        this.bulletedList = bulletedList;
    }

    public LinkedList<ChangeLogRow> getRows() {
        return rows;
    }

    public void setRows(LinkedList<ChangeLogRow> rows) {
        this.rows = rows;
    }




}
