package com.permission.beans;

import lombok.*;

import java.util.Set;

/**
 * @author zt1994 2018/8/13 21:07
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;

    private String message;

    private Set<String> receivers;


}
