package dexter.appsmoniac.bookpedia.model.pojo;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Book {

    private String id;

    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

}
