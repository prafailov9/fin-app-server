package com.project.app.exceptions;

public class SaveForEntityFailedException extends RuntimeException{

    public SaveForEntityFailedException() {
        super("Could not save entity");
    }

}
