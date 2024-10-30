package com.genzen.zenspire.data.seeder

import com.genzen.zenspire.data.models.article.Article
import com.genzen.zenspire.data.models.community.Post

class CommunitySeed {

    private val posts = listOf(
        Post(
            "1",
            "Mengatasi Kecemasan Sosial di Kampus",
            "Anonim",
            "2 jam yang lalu",
            listOf("Pengalaman", "Tantangan Keseharian"),
            25,
            5,
            "Saya selalu merasa cemas setiap kali harus berinteraksi dengan teman-teman di kampus. Rasa takut dinilai negatif membuat saya sering kali menghindari acara-acara sosial dan lebih memilih untuk sendirian. Hal ini membuat saya merasa kesepian dan terisolasi. \n\nSaya mencoba untuk lebih terbuka dan menghadapi ketakutan saya dengan bergabung dalam beberapa kegiatan kampus, namun rasa cemas itu masih saja muncul. Apakah ada yang mengalami hal serupa? Bagaimana cara kalian mengatasi kecemasan sosial ini? Saya sangat membutuhkan saran dan dukungan."
        ),
        Post(
            "2",
            "Tips Belajar Efektif untuk Ujian Akhir",
            "Mahasiswa Pintar",
            "5 jam yang lalu",
            listOf("Tips dan Strategi", "Edukasi"),
            40,
            12,
            "Ujian akhir semester sudah di depan mata dan saya merasa perlu berbagi tips belajar efektif yang mungkin bisa membantu teman-teman. Pertama, buatlah jadwal belajar yang terstruktur dan patuhi dengan disiplin. Kedua, gunakan teknik belajar aktif seperti membuat catatan, mind mapping, atau diskusi kelompok. \n\nKetiga, jangan lupa untuk istirahat yang cukup dan menjaga kesehatan tubuh dengan olahraga dan makan sehat. Keempat, cobalah untuk menghindari prokrastinasi dengan menetapkan target belajar harian. Semoga tips ini bermanfaat dan membantu teman-teman dalam menghadapi ujian akhir. Selamat belajar!"
        ),
        Post(
            "3",
            "Cara Menghadapi Teman yang Suka Mengatur",
            "Siti",
            "1 hari yang lalu",
            listOf("Tantangan Keseharian"),
            18,
            4,
            "Saya memiliki teman yang selalu ingin mengatur segala sesuatu, mulai dari kegiatan bersama hingga keputusan-keputusan kecil. Kadang-kadang saya merasa tidak nyaman dan tertekan dengan sikapnya. Saya sudah mencoba berbicara baik-baik, tapi sepertinya dia tidak menyadari atau tidak peduli dengan perasaan saya.\n\nBagaimana cara menghadapi teman yang suka mengatur tanpa merusak hubungan pertemanan? Saya butuh saran agar bisa menjaga hubungan baik tetapi juga tetap bisa merasa nyaman dan dihargai."
        ),
        Post(
            "4",
            "Pengalaman Menjalani Terapi untuk Depresi",
            "Anonim",
            "2 hari yang lalu",
            listOf("Pengalaman", "Terapi dan Perawatan"),
            30,
            7,
            "Saya ingin berbagi pengalaman saya menjalani terapi untuk depresi. Awalnya, saya merasa takut dan tidak yakin akan efektifitas terapi. Namun setelah beberapa sesi, saya mulai merasakan perubahan positif dalam diri saya. Terapi membantu saya memahami akar masalah yang saya hadapi dan memberikan alat untuk mengelola emosi saya dengan lebih baik.\n\nMeskipun tidak mudah, dukungan dari terapis dan komitmen untuk terus menjalani proses ini membuat saya merasa lebih baik dari hari ke hari. Bagi teman-teman yang mungkin sedang mengalami hal yang sama, jangan ragu untuk mencari bantuan profesional. Kesehatan mental kita sangat penting dan patut diperjuangkan."
        ),
        Post(
            "5",
            "Bagaimana Menghadapi Rasa Malas Berolahraga?",
            "Rina Fit",
            "3 hari yang lalu",
            listOf("Tips dan Strategi", "Motivasi dan Nasehat"),
            20,
            3,
            "Akhir-akhir ini saya merasa sangat malas untuk berolahraga, padahal saya tahu betapa pentingnya aktivitas fisik untuk kesehatan tubuh dan mental. Setiap kali saya mencoba memulai, saya selalu menemukan alasan untuk menunda atau berhenti di tengah jalan. Bagaimana cara mengatasi rasa malas ini? \n\nAdakah yang punya tips atau trik untuk tetap termotivasi berolahraga? Saya ingin kembali ke rutinitas olahraga yang sehat tetapi selalu kesulitan menjaga konsistensi."
        ),
        Post(
            "6",
            "Menghadapi Ketidakpastian Masa Depan Setelah Lulus",
            "Mahasiswa Galau",
            "4 hari yang lalu",
            listOf("Pengalaman", "Tantangan Keseharian"),
            22,
            6,
            "Sebagai mahasiswa tingkat akhir, saya mulai merasa cemas tentang masa depan setelah lulus. Ketidakpastian mengenai pekerjaan, karir, dan kehidupan mandiri sering kali membuat saya khawatir. Banyak teman saya juga merasakan hal yang sama, sehingga kami sering berdiskusi mengenai kekhawatiran ini.\n\nBagaimana cara kalian menghadapi ketidakpastian masa depan? Adakah yang punya saran atau pengalaman yang bisa dibagikan untuk membantu kami merasa lebih tenang dan percaya diri menghadapi masa depan?"
        ),
        Post(
            "7",
            "Cara Mengelola Konflik dengan Teman Sekamar",
            "Anonim",
            "5 hari yang lalu",
            listOf("Tantangan Keseharian"),
            15,
            2,
            "Tinggal bersama teman sekamar bisa menjadi tantangan tersendiri, terutama jika terjadi konflik. Saya sering kali berselisih paham dengan teman sekamar mengenai hal-hal kecil seperti kebersihan, jadwal tidur, dan penggunaan ruang bersama. Konflik ini kadang-kadang membuat suasana menjadi tidak nyaman.\n\nBagaimana cara mengelola konflik dengan teman sekamar agar tidak berlarut-larut dan tetap menjaga hubungan baik? Saya butuh saran dan pengalaman dari teman-teman yang pernah mengalami hal serupa."
        )
    )

    fun getArticles(): List<Post> {
        return posts
    }

    fun getArticle(id: Int): Post {
        return posts[id]
    }
}