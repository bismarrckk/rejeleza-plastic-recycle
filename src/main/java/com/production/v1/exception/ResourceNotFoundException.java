package com.production.v1.exception;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1031223678890664941L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}

/*
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}*/
/*
try {
// Check if the file's name contains invalid characters
if(fileName.contains("..")) {
    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
}

DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

return dbFileRepository.save(dbFile);
} catch (IOException ex) {
throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
}*/