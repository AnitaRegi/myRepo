package com.oracle.surveys.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Role implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private long id;
    private String name;
    private String description;
}
