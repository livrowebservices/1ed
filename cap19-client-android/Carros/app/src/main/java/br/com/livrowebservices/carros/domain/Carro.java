package br.com.livrowebservices.carros.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Carro implements Parcelable {

    public Long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlFoto;
    public String urlInfo;
    public String urlVideo;
    public String latitude;
    public String longitude;

    // Flag para a action bar de contexto
    public boolean selected;

    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' + ", desc='" + desc + '\'' + '}';
    }

    public Carro() {

    }

    public Carro(Parcel parcel) {
        this.id = parcel.readLong();
        this.tipo = parcel.readString();
        this.nome = parcel.readString();
        this.desc = parcel.readString();
        this.urlFoto = parcel.readString();
        this.urlVideo = parcel.readString();
        this.urlInfo = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(this.tipo);
        dest.writeString(this.nome);
        dest.writeString(this.desc);
        dest.writeString(this.urlFoto);
        dest.writeString(this.urlVideo);
        dest.writeString(this.urlInfo);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    public static final Parcelable.Creator<Carro> CREATOR = new Parcelable.Creator<Carro>() {
        @Override
        public Carro createFromParcel(Parcel source) {
            return new Carro(source);
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };

    public double getLatitude() {
        try {
            return Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getLongitude() {
        try {
            return Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}