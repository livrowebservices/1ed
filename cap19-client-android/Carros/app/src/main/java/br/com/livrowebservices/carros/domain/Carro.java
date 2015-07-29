package br.com.livrowebservices.carros.domain;

import java.io.Serializable;

public class Carro implements Serializable {
    private static final long serialVersionUID = 6601006766832473959L;

    public long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlFoto;
    public String urlInfo;
    public String urlVideo;
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' + ", desc='" + desc + '\'' + '}';
    }

    /*
    // PARCELABLE
        public Car(Parcel parcel){
            setModel(parcel.readString());
            setBrand(parcel.readString());
            setDescription(parcel.readString());
            setCategory(parcel.readInt());
            setTel(parcel.readString());
            setPhoto(parcel.readInt());
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( getModel() );
            dest.writeString( getBrand() );
            dest.writeString( getDescription() );
            dest.writeInt( getCategory() );
            dest.writeString( getTel() );
            dest.writeInt( getPhoto() );
        }
        public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>(){
            @Override
            public Car createFromParcel(Parcel source) {
                return new Car(source);
            }
            @Override
            public Car[] newArray(int size) {
                return new Car[size];
            }
        };
     */
}