package com.jiehang.beans;

import lombok.*;

import java.util.Set;

/**
 * @ClassName Mail
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-13 23:48
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mail {

    private String subject;

    private String message;

    private Set<String> receivers;

}
