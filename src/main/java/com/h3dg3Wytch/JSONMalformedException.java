package com.h3dg3Wytch;

/**
 * Created by h3dg3wytch on 2/24/17.
 */
public class JSONMalformedException extends Throwable {

    public JSONMalformedException() {
        super("This JSON is malformed");
        System.out.println("This JSON is malformed! Please enter one with a correct schema!");
    }


}
