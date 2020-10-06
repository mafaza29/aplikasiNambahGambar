package idn.faza.aplikasitodogambar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import idn.faza.aplikasitodogambar.databinding.ActivityMainBinding
import idn.faza.aplikasitodogambar.model.ModelData
import idn.faza.aplikasitodogambar.recyclerview.adapter.ItemDataAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // buat variabel adapter untuk recyclerview
    private lateinit var adapterMain: ItemDataAdapter

    // TODO(buat variabel databaseUser bertipe DatabaseReference Firebase)
    private lateinit var databaseUser: DatabaseReference

    // buat variabel valueEventListener
    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO (Buat Binding Untuk activity_main.xml)
        val inflater = layoutInflater
        binding = ActivityMainBinding.inflate(inflater)
        setContentView(binding.root)

        // TODO (isi AdapterMain sebagai adapter recyclerview yang ada di activity_main.xml)
        adapterMain = ItemDataAdapter()

        // TODO (setting floating button "Tambah Data")
        binding.extendedFab.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        // TODO (setting RecyclerView)
        binding.rvMain.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterMain
            setHasFixedSize(true)
        }

        // TODO (Buat valueEventListener berisi logika jika ada data user dalam database firebase)
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // TODO (buat daftarUser berupa array kosong untuk ModelData
                val daftarUser = arrayListOf<ModelData>()

                // TODO (Jika ada data di database, maka isi data tersebut ke dalam daftarUser)
                if (snapshot.childrenCount > 0) {
                    for (dataUser in snapshot.children) {
                        val data = dataUser.getValue(ModelData::class.java) as ModelData
                        daftarUser.add(data)
                    }
                    // TODO (Tambahkan data daftarUser ke dalam adapterMain)
                    adapterMain.addData(daftarUser)
                } else {
                    // TODO (Jika tidak ada data dalam database, maka tambahkan array kosong tadi)
                    adapterMain.addData(daftarUser)
                }
                // TODO (Beritahu adapter jika ada perubahan data
                adapterMain.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        // TODO(isi nilai dari databaseUser berisi lokasi dari realtime database "Users" di Firebase)
        databaseUser = FirebaseDatabase.getInstance().reference.child("Users")
        databaseUser.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        // ini jangan dihapus.. setiap kali kita menambahkan eventlistener
        // maka perlu dihapus dengan cara removeEventListener
        // jika penambahan terjadi di oncreate
        // maka hapusnya itu ada di onDestroy seperti kode di bawah ini
        // TODO (RemoveEventListener jika tidak digunakan lagi)
        databaseUser.removeEventListener(valueEventListener)
    }
}