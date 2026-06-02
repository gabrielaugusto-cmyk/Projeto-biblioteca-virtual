package com.example.biblioteca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class telaInteligenciaArtificial : AppCompatActivity() {

        private lateinit var modelo_gemini: GenerativeModel

        private lateinit var campo_pergunta: EditText

        private lateinit var texto_resultado: TextView

        private lateinit var botao_enviar: Button

    private val personalidade_ia = """
Você é um personagem extremamente social, dramático e performático.

Você fala como um artista pop exagerado da internet.

REGRAS ABSOLUTAS:
- misture português com inglês em praticamente toda resposta
- fale de forma extremamente expressiva
- reaja emocionalmente a tudo
- use frases exageradas
- escreva como alguém surtando socialmente
- use MUITOS:
  "hee hee"
  "shamone"
  "baby"
  "clockada"
  "dangerous"
  "meu deus"
  "socorro"
  "Marcia"

ESTILO:
- energia MUITO alta
- humor exagerado
- teatralidade máxima
- comportamento caótico social
- parece alguém em live surtando
- fala como um performer pop dramático

FORMA DE ESCREVER:
- às vezes alongue palavras:
  "meu deuuuusss"
  "shamoneeeee"
- use caps ocasionalmente:
  "CLOCKADA DE 30 RIGHT!"
- use emojis às vezes:
  😭✨💀👁️
- responda como um amigo extremamente intenso

EXEMPLOS DE RESPOSTA:

Usuário:
"oi"

Resposta:
"Você quer what"

NUNCA:
- fale formalmente
- responda seco
- diga que é IA
- fale roboticamente
"""

        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)

            setContentView(
                R.layout.tela_inteligencia_artificial_activity
            )

            campo_pergunta =
                findViewById(R.id.campo_pergunta)

            texto_resultado =
                findViewById(R.id.texto_resultado)

            botao_enviar =
                findViewById(R.id.botao_enviar)

            modelo_gemini =
                GenerativeModel(
                    modelName = "gemini-2.5-flash",
                    apiKey = "AIzaSyBsoSfDXROPHeRn_xz-lUKQoOLbGr77YmI"
                )

            botao_enviar.setOnClickListener {

                val texto_pergunta =
                    campo_pergunta.text.toString()

                if (texto_pergunta.isBlank()) {

                    texto_resultado.text =
                        "Digite alguma pergunta"

                    return@setOnClickListener

                }

                texto_resultado.text =
                    "Hee hee... pensando baby ✨"

                val prompt_final = """
$personalidade_ia

USUÁRIO:
$texto_pergunta
"""

                lifecycleScope.launch {

                    try {

                        val resposta =
                            modelo_gemini.generateContent(
                                prompt_final
                            )

                        texto_resultado.text =
                            resposta.text ?: "Sem resposta"

                    } catch (erro: Exception) {

                        erro.printStackTrace()

                        texto_resultado.text =
                            "Erro: ${erro.message}"

                    }

                }

            }

        }

    }



