package ntou.cs.weatherapp.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherBean {

    @Expose
    @SerializedName("records")
    private RecordsEntity records;
    @Expose
    @SerializedName("result")
    private ResultEntity result;
    @Expose
    @SerializedName("success")
    private String success;

    public RecordsEntity getRecords() {
        return records;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getSuccess() {
        return success;
    }

    public static class RecordsEntity {
        @Expose
        @SerializedName("locations")
        private List<LocationsEntity> locations;

        public List<LocationsEntity> getLocations() {
            return locations;
        }
    }

    public static class LocationsEntity {
        @Expose
        @SerializedName("location")
        private List<LocationEntity> location;
        @Expose
        @SerializedName("dataid")
        private String dataid;
        @Expose
        @SerializedName("locationsName")
        private String locationsName;
        @Expose
        @SerializedName("datasetDescription")
        private String datasetDescription;

        public List<LocationEntity> getLocation() {
            return location;
        }

        public String getDataid() {
            return dataid;
        }

        public String getLocationsName() {
            return locationsName;
        }

        public String getDatasetDescription() {
            return datasetDescription;
        }
    }

    public static class LocationEntity {
        @Expose
        @SerializedName("weatherElement")
        private List<WeatherElementEntity> weatherElement;
        @Expose
        @SerializedName("lon")
        private String lon;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("geocode")
        private String geocode;
        @Expose
        @SerializedName("locationName")
        private String locationName;

        public List<WeatherElementEntity> getWeatherElement() {
            return weatherElement;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }

        public String getGeocode() {
            return geocode;
        }

        public String getLocationName() {
            return locationName;
        }
    }

    public static class WeatherElementEntity {
        @Expose
        @SerializedName("time")
        private List<TimeEntity> time;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("elementName")
        private String elementName;

        public List<TimeEntity> getTime() {
            return time;
        }

        public String getDescription() {
            return description;
        }

        public String getElementName() {
            return elementName;
        }
    }

    public static class TimeEntity {
        @Expose
        @SerializedName("elementValue")
        private List<ElementValueEntity> elementValue;
        @Expose
        @SerializedName("endTime")
        private String endTime;
        @Expose
        @SerializedName("startTime")
        private String startTime;

        public List<ElementValueEntity> getElementValue() {
            return elementValue;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getStartTime() {
            return startTime;
        }
    }

    public static class ElementValueEntity {
        @Expose
        @SerializedName("measures")
        private String measures;
        @Expose
        @SerializedName("value")
        private String value;

        public String getMeasures() {
            return measures;
        }

        public String getValue() {
            return value;
        }
    }

    public static class ResultEntity {
        @Expose
        @SerializedName("fields")
        private List<FieldsEntity> fields;
        @Expose
        @SerializedName("resource_id")
        private String resource_id;

        public List<FieldsEntity> getFields() {
            return fields;
        }

        public String getResource_id() {
            return resource_id;
        }
    }

    public static class FieldsEntity {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("id")
        private String id;

        public String getType() {
            return type;
        }

        public String getId() {
            return id;
        }
    }
}
