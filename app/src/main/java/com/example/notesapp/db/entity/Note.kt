package com.example.notesapp.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
class Note(): Parcelable {

    @ColumnInfo(name="note_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name="note_name")
    var name: String? = null

    @ColumnInfo(name="note_description")
    var description: String?=null

    constructor(id: Long?, name: String?, description: String?) : this() {
        this.id = id
        this.name = name
        this.description = description

    }

    constructor(parcel: Parcel): this() {
        id = parcel.readLong()
        name = parcel.readString()
        description = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeLong(it) }
        parcel.writeString(name)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}