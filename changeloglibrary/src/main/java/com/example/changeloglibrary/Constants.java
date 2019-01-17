package com.example.changeloglibrary;

public class Constants {

    /**
     *  Resource id for changelog.xml file.
     *
     *  You shouldn't modify this value.
     *  You can use changeLogResourceId attribute in ChangeLogListView
     **/
    public static final int mChangeLogFileResourceId= R.raw.changelog;

    /**
     *  Layout resource id for changelog item rows.
     *
     *  You shouldn't modify this value.
     *  You can use rowLayoutId attribute in ChangeLogListView
     **/
    public static final int mRowLayoutId=R.layout.changelogrow_layout;

    /**
     * Layout resource id for changelog header rows.
     *
     *  You shouldn't modify this value.
     *  You can use rowHeaderLayoutId attribute in ChangeLogListView
     **/
    public static final int mRowHeaderLayoutId=R.layout.changelogrowheader_layout;


    /**
     *  String resource id for text Version in header row.
     *
     * You shouldn't modify this value.
     *  You can use changelog_header_version in strings.xml
     */
    public static final int mStringVersionHeader= R.string.changelog_header_version;
}
