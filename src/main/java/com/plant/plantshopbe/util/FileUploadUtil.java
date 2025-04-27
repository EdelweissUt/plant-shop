package com.plant.plantshopbe.util;

import com.plant.plantshopbe.exception.AppException;
import com.plant.plantshopbe.exception.ErrorCode;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {
    public static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    // Mẫu regex cho video
    public static final String VIDEO_PATTERN = "([^\\s]+(\\.(?i)(mp4|avi|mov|mkv))$)";


    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file, String pattern) {
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new AppException(ErrorCode.FILE_MAX);
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, pattern)) {
            throw new AppException(ErrorCode.FILE_NOT_SUPPORT);
        }
    }

    public static String getFileName(final String name) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, name, date);
    }
    public static String getMediaType(final MultipartFile file) {
        final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension == null) return "Unknown";

        final String ext = extension.toLowerCase();
        if (ext.matches("jpg|jpeg|png|gif|bmp")) return "Image";
        if (ext.matches("mp4|avi|mov|mkv")) return "Video";
        return "Unknown";
    }
}
