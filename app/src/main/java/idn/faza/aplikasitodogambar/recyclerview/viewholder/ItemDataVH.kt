package idn.faza.aplikasitodogambar.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import idn.faza.aplikasitodogambar.R
import idn.faza.aplikasitodogambar.databinding.ItemDataBinding
import idn.faza.aplikasitodogambar.model.ModelData

/**
 * Created by Imam Fahrur Rofi on 04/09/2020.
 */
class ItemDataVH(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ModelData) {
        Glide.with(binding.root.context)
            .load(data.profile_image)
            .centerCrop()
            .placeholder(R.drawable.gambar_placeholder)
            .into(binding.imgProfile)

        // TODO(Masukkan data lainnya seperti name, address, dan class ke dalam item recyclerview
        binding.run {
            txtNama.text = data.profile_name
            txtKelas.text = data.profile_class
            txtAlamat.text = data.profile_address
        }

    }
}