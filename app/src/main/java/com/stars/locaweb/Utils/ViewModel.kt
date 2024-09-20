import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stars.locaweb.Utils.EmailItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EmailViewModel : ViewModel() {
    private val _emails = MutableStateFlow<List<EmailItem>>(emptyList())
    val emails: StateFlow<List<EmailItem>> get() = _emails

    private val _sentEmails = MutableStateFlow<List<EmailItem>>(emptyList())

    fun getSentEmails(): StateFlow<List<EmailItem>> {
        return _sentEmails
    }

    init {
        addEmail(EmailItem("Promoção Especial!", "promo@company.com", "Aproveite nossa oferta especial com 50% de desconto.", true))
        addEmail(EmailItem("Ofertas Imperdíveis", "offers@company.com", "Não perca as ofertas imperdíveis da semana!", true))
        addSentEmail(EmailItem("Nota Pendente", "fiap@company.com", "Gostaria de solicitar a correção da entrega 4"))
        addSentEmail(EmailItem("Entrega Sprint 4", "italo@company.com", "Não esqueça de enviar a Sprint 4"))
    }

    // Função para adicionar um email à lista de todos os emails
    fun addEmail(email: EmailItem) {
        _emails.value = _emails.value + email
    }

    // Função para adicionar um email à lista de emails enviados
    fun addSentEmail(email: EmailItem) {
        _sentEmails.value = _sentEmails.value + email
    }

    // Função para marcar um email como spam
    fun markAsSpam(email: EmailItem) {
        _emails.value = _emails.value.map {
            if (it == email) it.copy(isSpam = true) else it
        }
    }

    fun getSpamEmails(): StateFlow<List<EmailItem>> {
        return _emails
            .map { emailList -> emailList.filter { it.isSpam } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }
}