package com.genzen.zenspire.data.seeder

import com.genzen.zenspire.data.models.chat.Contact

class ContactSeed {

    private val contacts = listOf(
        Contact("11", "dr. Aisya Aprilia", "Klinik GMC UGM"),
        Contact("12", "dr. Linda Bonea", "Klinik GMC UGM"),
        Contact("13", "dr. Budi Santoso", "Klinik Sehat Sentosa"),
        Contact("14", "dr. Maritza Angel", "Psikolog di GMC"),
        Contact("15", "dr. Andini Putri", "Psikolog di Health Center"),
        Contact("16", "dr. Dian Hartanto", "Konselor Pendidikan di Edu Center"),
        Contact("17", "dr. Eka Wijaya", "Konsultan Keuangan di Moneywise"),
        Contact("18", "dr. Farah Ramadhani", "Psikolog di Wellbeing Center"),
        Contact("19", "dr. Gusti Ananda", "Konselor di Student Support Center"),
        Contact("20", "dr. Heru Wibowo", "Dosen di Universitas XYZ"),
        Contact("21", "dr. Intan Pratama", "Psikolog di Campus Life Center"),
        Contact("22", "dr. Joko Susilo", "Psikolog di Personal Growth Clinic")
    )

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun getContact(id: Int): Contact {
        return contacts[id]
    }
}