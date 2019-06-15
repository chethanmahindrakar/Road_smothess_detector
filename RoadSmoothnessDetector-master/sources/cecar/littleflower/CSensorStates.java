package cecar.littleflower;

import android.hardware.Sensor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;

public class CSensorStates implements Parcelable {
    public static final Creator<CSensorStates> CREATOR = new C00011();
    private boolean[] act_list;
    private String[] names;
    private int nsen;
    private int[] rates;
    private int[] types;

    /* renamed from: cecar.littleflower.CSensorStates$1 */
    static class C00011 implements Creator<CSensorStates> {
        C00011() {
        }

        public CSensorStates createFromParcel(Parcel source) {
            return new CSensorStates(source);
        }

        public CSensorStates[] newArray(int size) {
            return new CSensorStates[size];
        }
    }

    public CSensorStates(boolean val) {
        this.names = null;
        this.types = null;
        this.act_list = null;
        this.nsen = 0;
        this.rates = null;
        this.nsen = 3;
        this.names = new String[this.nsen];
        this.types = new int[this.nsen];
        this.act_list = new boolean[this.nsen];
        this.rates = new int[this.nsen];
        this.names[0] = "ivmeolceer";
        this.names[1] = "donuolcer";
        this.names[2] = "termo";
        this.act_list[0] = true;
        this.act_list[1] = false;
        this.act_list[2] = true;
        this.types[0] = 0;
        this.types[1] = 1;
        this.types[2] = 2;
        this.rates[0] = 5;
        this.rates[1] = 5;
        this.rates[2] = 5;
    }

    public CSensorStates(List<Sensor> aSList) {
        this.names = null;
        this.types = null;
        this.act_list = null;
        this.nsen = 0;
        this.rates = null;
        this.nsen = aSList.size();
        if (this.nsen > 0) {
            this.names = new String[this.nsen];
            this.types = new int[this.nsen];
            this.act_list = new boolean[this.nsen];
            this.rates = new int[this.nsen];
            for (int i = 0; i < this.nsen; i++) {
                this.names[i] = ((Sensor) aSList.get(i)).getName();
                this.types[i] = ((Sensor) aSList.get(i)).getType();
                this.act_list[i] = false;
                this.rates[i] = 3;
            }
        }
    }

    String[] getNames() {
        return this.names;
    }

    String getName(int i) {
        return this.names[i];
    }

    String getNameByType(int typ) {
        for (int i = 0; i < this.nsen; i++) {
            if (this.types[i] == typ) {
                return this.names[i];
            }
        }
        return "Undefined Type";
    }

    void setActive(int i, boolean val) {
        this.act_list[i] = val;
    }

    void setRate(int i, int val) {
        this.rates[i] = val;
    }

    void setActToggle(int i) {
        this.act_list[i] = !this.act_list[i];
    }

    void setRate(int val) {
        for (int i = 0; i < this.nsen; i++) {
            this.rates[i] = val;
        }
    }

    int getRate(int i) {
        return this.rates[i];
    }

    Boolean getActive(int i) {
        return Boolean.valueOf(this.act_list[i]);
    }

    int getNum() {
        return this.nsen;
    }

    int getNumAct() {
        int nact = 0;
        for (int i = 0; i < this.nsen; i++) {
            if (this.act_list[i]) {
                nact++;
            }
        }
        return nact;
    }

    public int getType(int i) {
        return this.types[i];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.nsen);
        out.writeStringArray(this.names);
        out.writeIntArray(this.types);
        out.writeBooleanArray(this.act_list);
        out.writeIntArray(this.rates);
    }

    private CSensorStates(Parcel source) {
        this.names = null;
        this.types = null;
        this.act_list = null;
        this.nsen = 0;
        this.rates = null;
        this.nsen = source.readInt();
        if (this.nsen > 0) {
            this.names = new String[this.nsen];
            this.types = new int[this.nsen];
            this.rates = new int[this.nsen];
            this.act_list = new boolean[this.nsen];
            source.readStringArray(this.names);
            source.readIntArray(this.types);
            source.readBooleanArray(this.act_list);
            source.readIntArray(this.rates);
        }
    }
}
