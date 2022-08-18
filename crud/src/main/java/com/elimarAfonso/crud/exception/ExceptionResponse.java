package com.elimarAfonso.crud.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date timesTamp;
	private String message;
	private String details;

}
