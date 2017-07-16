package com.oracle.comparator;

import java.util.Comparator;

import com.oracle.entity.MessageEntity;

public class DateComparator implements Comparator<MessageEntity>{

    @Override
    public int compare(MessageEntity m1, MessageEntity m2) {
        return m1.getMessage_date().compareTo(m2.getMessage_date());
    }
}