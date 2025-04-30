package br.com.michproducoes.appdenotciasusurio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.michproducoes.appdenotciasusurio.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recuperarNoticia()
    }

    private fun recuperarNoticia() {
        db.collection("noticias").document("noticias").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documento = task.result
                    if (documento != null && documento.exists()) {
                        val titulo = documento.getString("titulo") ?: "Título não encontrado"
                        val noticias = documento.getString("noticias") ?: "Notícia não encontrada"
                        val data = documento.getString("data") ?: "Data não encontrada"
                        val autor = documento.getString("autor") ?: "Autor não encontrado"

                        binding.editTituloNoticia.setText(titulo)
                        binding.editNoticia.setText(noticias)
                        binding.editDatadaNoticia.setText(data)
                        binding.editAutorNoticia.setText(autor)

                    }
                }
            }
    }
}
