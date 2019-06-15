package cecar.littleflower;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;

public class CLocProvStates implements Parcelable {
    public static final Creator<CLocProvStates> CREATOR = new C00001();
    private boolean[] act_list;
    private float[] mindist;
    private long[] mintime;
    private String[] names;
    private int nman;
    private int[] types;

    /* renamed from: cecar.littleflower.CLocProvStates$1 */
    static class C00001 implements Creator<CLocProvStates> {
        C00001() {
        }

        public CLocProvStates createFromParcel(Parcel source) {
            return new CLocProvStates(source);
        }

        public CLocProvStates[] newArray(int size) {
            return new CLocProvStates[size];
        }
    }

    public CLocProvStates(List<String> LocMan) {
        this.names = null;
        this.types = null;
        this.act_list = null;
        this.mintime = null;
        this.mindist = null;
        this.nman = 0;
        this.nman = LocMan.size();
        if (this.nman > 0) {
            this.names = new String[this.nman];
            this.act_list = new boolean[this.nman];
            this.mintime = new long[this.nman];
            this.mindist = new float[this.nman];
            this.types = new int[this.nman];
            for (int i = 0; i < this.nman; i++) {
                this.names[i] = (String) LocMan.get(i);
                this.act_list[i] = false;
                this.mintime[i] = 0;
                this.mindist[i] = 0.0f;
                this.types[i] = i;
            }
        }
    }

    String[] getNames() {
        return this.names;
    }

    String getName(int i) {
        return this.names[i];
    }

    int getNum() {
        return this.nman;
    }

    int getNumAct() {
        int nact = 0;
        for (int i = 0; i < this.nman; i++) {
            if (this.act_list[i]) {
                nact++;
            }
        }
        return nact;
    }

    boolean getActive(int i) {
        return this.act_list[i];
    }

    long getMinTime(int i) {
        return this.mintime[i];
    }

    float getMinDist(int i) {
        return this.mindist[i];
    }

    void setActive(int i, boolean val) {
        this.act_list[i] = val;
    }

    void setActToggle(int i) {
        this.act_list[i] = !this.act_list[i];
    }

    void setToggle(int i) {
        this.act_list[i] = !this.act_list[i];
    }

    void setCriterion(int i, float mind, long mint) {
        this.mindist[i] = mind;
        this.mintime[i] = mint;
    }

    void setCriterion(float mind, long mint) {
        for (int i = 0; i < this.nman; i++) {
            this.mindist[i] = mind;
            this.mintime[i] = mint;
        }
    }

    public boolean isExist(String provider) {
        for (int i = 0; i < this.nman; i++) {
            if (this.names[i].equals(provider)) {
                return true;
            }
        }
        return false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int arg1) {
        out.writeInt(this.nman);
        out.writeStringArray(this.names);
        out.writeBooleanArray(this.act_list);
        out.writeLongArray(this.mintime);
        out.writeFloatArray(this.mindist);
        out.writeIntArray(this.types);
    }

    private CLocProvStates(Parcel source) {
        this.names = null;
        this.types = null;
        this.act_list = null;
        this.mintime = null;
        this.mindist = null;
        this.nman = 0;
        this.nman = source.readInt();
        if (this.nman > 0) {
            this.names = new String[this.nman];
            this.act_list = new boolean[this.nman];
            this.mintime = new long[this.nman];
            this.mindist = new float[this.nman];
            this.types = new int[this.nman];
            source.readStringArray(this.names);
            source.readBooleanArray(this.act_list);
            source.readLongArray(this.mintime);
            source.readFloatArray(this.mindist);
            source.readIntArray(this.types);
        }
    }
}
