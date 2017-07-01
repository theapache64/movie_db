package com.theah64.movie_db.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by theapache64 on 11/22/2015.
 */
public class Request {

    private final HttpServletRequest request;
    private final String[] requiredParams;
    private List<String> missingOrInvalidParams;

    public Request(HttpServletRequest request, String[] requiredParams) throws Exception {
        this.request = request;
        this.requiredParams = requiredParams;

        if (!hasAllParams()) {
            throw new Exception(getErrorReport());
        }
    }

    /**
     * True if all the requiredParam exist and !empty other wise false
     *
     * @return Boolean
     */
    public boolean hasAllParams() {
        return has(requiredParams);
    }


    private String getErrorReport() {

        if (missingOrInvalidParams != null && !missingOrInvalidParams.isEmpty()) {
            final StringBuilder errorReportBuilder = new StringBuilder("Missing or empty value for ");
            for (final String param : missingOrInvalidParams) {
                errorReportBuilder.append(param).append(",");
            }
            //Removing last comma and returns the error report.
            return errorReportBuilder.substring(0, errorReportBuilder.length() - 1);
        }

        return null;
    }

    public String getStringParameter(String key) {
        return this.request.getParameter(key);
    }

    public boolean getBooleanParameter(String key) {
        return Boolean.parseBoolean(getStringParameter(key));
    }

    public boolean has(final String... requiredParamKeys) {

        Map<String, String[]> paramMap = request.getParameterMap();

        if (this.missingOrInvalidParams != null && !this.missingOrInvalidParams.isEmpty()) {
            //Clears old history if exist.
            this.missingOrInvalidParams.clear();
        }

        for (final String reqParam : requiredParamKeys) {

            final String[] reqParamValue = paramMap.get(reqParam);

            if (reqParamValue == null || reqParamValue[0].trim().isEmpty()) {

                //Lazy init
                if (this.missingOrInvalidParams == null) {
                    this.missingOrInvalidParams = new ArrayList<>();
                }

                //Invalid or missing Adding bad param name to list
                this.missingOrInvalidParams.add(reqParam);
            }
        }

        return this.missingOrInvalidParams == null || missingOrInvalidParams.isEmpty();
    }


    /**
     * Used to check if the parameter not null and not empty
     */
    public boolean has(final String paramKey) {
        final String paramValue = this.request.getParameter(paramKey);
        return paramValue != null && !paramValue.trim().isEmpty();
    }

    public long getLongParameter(String key) {
        final String value = getStringParameter(key);
        return value != null ? Long.parseLong(value) : -1;
    }

}
