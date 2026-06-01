# Central de Conversores Dinâmica / Dynamic Converter Hub

A descontraída e robusta central de conversões para Android Nativo, inspirada no ecossistema da Xiaomi, mas com uma dose extra de personalidade.

An informal yet robust conversion hub for Native Android, inspired by the Xiaomi ecosystem, but packed with an extra dose of personality.

---

## 🇧🇷 Versão em Português

### Sobre o Projeto
Este aplicativo foi desenvolvido do zero em **Kotlin** com o objetivo de criar uma ferramenta de utilidades rápida, fluida e visualmente marcante. A inspiração estrutural e de usabilidade veio diretamente do aplicativo de **Calculadora da Xiaomi**, utilizando seletores do tipo drop-down (Spinners) para trocas rápidas de unidades. 

Para quebrar a monotonia de aplicativos utilitários genéricos, o app adota uma identidade visual vibrante (Azul *Mango Loco*) e utiliza memes clássicos da internet como ícones e guias temáticos para cada categoria de conversão. O projeto também conta com suporte total a **internacionalização (i18n)**, adaptando todos os seus textos automaticamente caso o idioma do smartphone esteja em Inglês.

### Estrutura do App e Funcionalidades
O aplicativo é dividido em **3 páginas principais**, acessadas facilmente pelo menu de navegação:

1. ** Página da Calculadora:** Uma calculadora aritmética completa e tradicional (soma, subtração, multiplicação e divisão), ideal para contas rápidas do dia a dia com um layout limpo e intuitivo.
2. ** Página de Conversores (Menu Temático):** O hub central que abriga os conversores personalizados. Ao clicar nos cards temáticos, o usuário acessa:
   * **Massa (Isaac Newton):** Conversões bilaterais entre Quilograma (kg), Grama (g), Libra (lb) e Onça (oz).
   * **Comprimento (O Gato Longo):** Suporte a 10 unidades diferentes (Métrico e Imperial) usando uma lógica otimizada de unidade base.
   * **Velocidade (Relâmpago McQueen):** Conversão instantânea entre mph, m/s, km/h e Nós (kn).
   * **Temperatura (O Gelado):** Lógica blindada para Celsius, Fahrenheit e Kelvin, preparada para lidar com números negativos sem travamentos.
3. ** Página de Moedas (API ao Vivo):** Cotações do Dólar (USD) e Euro (EUR) capturadas em tempo real via API externa, permitindo que o usuário faça conversões manuais cruzadas de qualquer valor que quiser instantaneamente.

###Tecnologias e Conceitos Aplicados (Os Bastidores)
* **Componentes de Arquitetura (Fragments):** Navegação moderna e modular utilizando arquivos separados para cada tela, otimizando o consumo de memória do dispositivo.
* **Consumo de API REST (Retrofit 2 & GSON):** Conexão assíncrona com a *AwesomeAPI* para buscar dados JSON em segundo plano sem congelar a interface do usuário.
* **Cálculos em Tempo Real (`TextWatcher`):** Experiência de usuário fluida onde o resultado é calculado instantaneamente a cada caractere digitado, eliminando a necessidade de um botão "Calcular".
* **Internacionalização Dinâmica (`strings.xml`):** Separação completa de Strings do código Kotlin, permitindo a tradução de textos, dicas (`android:hint`) e resultados dinamicamente através do sistema de Locales do Android.
* **Desenho Responsivo (`ConstraintLayout`):** Telas matematicamente amarradas para se adaptarem perfeitamente a qualquer tamanho ou resolução de smartphone.

---

## 🇺🇸 English Version

### About the Project
This application was developed from scratch in **Kotlin** with the goal of creating a fast, fluid, and visually striking utility tool. The structural and usability inspiration came directly from the **Xiaomi Calculator** app, utilizing drop-down selectors (Spinners) for quick unit switching.

To break the monotony of generic utility apps, the project adopts a vibrant visual identity (inspired by the *Mango Loco* palette) and uses classic internet memes as icons and thematic guides for each conversion category. The project also features full **internationalization (i18n)** support, automatically adapting its interface if the smartphone's language is set to English.

### App Structure & Features
The application is divided into **3 main pages**, easily accessed through the navigation menu:

1. ** Calculator Page:** A complete, traditional arithmetic calculator (addition, subtraction, multiplication, and division), ideal for quick everyday calculations with a clean and intuitive layout.
2. ** Converters Page (Thematic Menu):** The central hub hosting the custom converters. By tapping on the thematic cards, the user accesses:
   * **Mass (Frozone):** Bilateral conversions between Kilogram (kg), Gram (g), Pound (lb), and Ounce (oz).
   * **Length (Long Cat):** Support for 10 different units (Metric and Imperial) using an optimized base-unit logic.
   * **Speed (Lightning McQueen):** Instant conversion between mph, m/s, km/h, and Knots (kn).
   * **Temperature (Isaac Newton):** Crash-proof logic for Celsius, Fahrenheit, and Kelvin, fully prepared to handle signed negative numbers.
3. ** Currency Page (Live API):** Real-time Dollar (USD) and Euro (EUR) rates fetched from an external API, allowing the user to perform instant manual cross-conversions for any amount they want.

### Architecture & Technical Stack (Behind the Scenes)
* **Android Architecture Components (Fragments):** Modern, modular navigation using independent files for each screen, optimizing device memory usage.
* **REST API Consumption (Retrofit 2 & GSON):** Asynchronous connection to *AwesomeAPI* to fetch and parse JSON data in a background thread without freezing the UI thread.
* **Real-Time Calculations (`TextWatcher`):** Fluid user experience where results are computed instantly as the user types, completely eliminating the need for a "Calculate" button.
* **Dynamic Internationalization (`strings.xml`):** Total separation of UI strings from Kotlin code, enabling dynamic translation of text fields, hints (`android:hint`), and runtime results via Android Locales.
* **Responsive UI (`ConstraintLayout`):** Mathematically constrained layouts that adapt seamlessly to any smartphone screen size or resolution.

---

## Screenshots / Visuals

| Calculadora / Calculator | Menu de Conversores / Converters | Conversão de Moedas / Live Currency |
|---|---|---|
| <img src="abacalculadora.jpeg" width="220"> | <img src="abaconverters.jpeg" width="220"> | <img src="abacurrency.jpeg" width="220"> |

---

## 💻 Como Rodar o Projeto / How to Run the Project

1. Clone este repositório / Clone this repository:
   ```bash
   git clone [https://github.com/matglowaski/Calculadora-Hub.git](https://github.com/matglowaski/Calculadora-Hub.git)
2. Abra o projeto no Android Studio / Open the project in Android Studio.

3. Aguarde a sincronização do Gradle (as dependências do Retrofit serão baixadas automaticamente) / Wait for Gradle Sync (Retrofit dependencies will download automatically).

4. Execute em um emulador ou dispositivo físico com Android 7.0+ / Run it on an emulator or physical device with Android 7.0+.
