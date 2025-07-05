# 🤖 AI Chatbot API (Gemini-based)

A simple Spring Boot REST API that connects to Google's Gemini model to provide conversational responses.  
Deployed with Docker on [Render](https://render.com).

---

## 🌐 Live Demo

[https://ai-chatbot-0mmy.onrender.com](https://ai-chatbot-0mmy.onrender.com)

---

## ✨ Features

- ✅ Chat with Gemini via REST API
- ✅ Session support for context continuity
- ✅ Upload custom context/instructions
- ✅ Reset conversation memory
- ✅ Docker-based deployment
- ✅ PostgreSQL & Redis integration ready

---

## 📦 API Endpoints

### `POST /gemini/chat`

Chat with Gemini using a prompt.

**Query Parameters:**
- `prompt` *(required)* — the user's message
- `sessionId` *(optional)* — use this to maintain conversation history

**Example:**

```bash
curl -X POST "https://ai-chatbot-0mmy.onrender.com/gemini/chat?prompt=Hello"
````

---

### `POST /gemini/upload-context`

Upload initial context and instructions (e.g., prompt tuning).

**Body:** plain text
**Query Parameter:** `instruction=Your instruction`

**Returns:** Generated `sessionId`

---

### `POST /gemini/reset`

Reset the conversation history for a session.

**Query Parameter:** `sessionId`

---

## ⚙️ Tech Stack

* Java 17 + Spring Boot
* Google Gemini API
* Redis (in-memory conversation store)
* PostgreSQL (future persistence support)
* Docker & Docker Compose
* Render (deployment)

---

## 📁 Project Structure

```
.
├── src/main/java/com/github/sonjaemark/ai_chatbot
│   ├── controller/
│   │   └── GeminiController.java
│   └── service/
│       ├── ChatMemoryManager.java
│       └── GeminiService.java
├── application.yml
├── Dockerfile
├── docker-compose.yml
├── .env              # For local dev only
└── README.md
```

---

## 🧪 Local Development

1. **Clone the repo**

```bash
git clone https://github.com/your-username/ai-chatbot.git
cd ai-chatbot
```

2. **Create a `.env` file**

```dotenv
DB_URL=r2dbc:postgresql://db:5432/mydb
DB_USERNAME=user
DB_PASSWORD=password
REDIS_HOST=redis
REDIS_PORT=6379

GEMINI_API_KEY=your-api-key
GEMINI_ENDPOINT=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent
```

3. **Start with Docker Compose**

```bash
docker-compose up --build
```

---

## 📡 Deployment (Render)

1. Push your code to a GitHub repo
2. Go to [Render Dashboard](https://dashboard.render.com)
3. Create a **new Web Service**

   * Use environment: `Docker`
   * Port: `8080`
4. Set these environment variables:

| Key               | Example Value                                          |
| ----------------- | ------------------------------------------------------ |
| `DB_URL`          | `r2dbc:postgresql://your-db-host:5432/mydb`            |
| `DB_USERNAME`     | `user`                                                 |
| `DB_PASSWORD`     | `password`                                             |
| `REDIS_HOST`      | `your-redis-host`                                      |
| `REDIS_PORT`      | `6379`                                                 |
| `GEMINI_API_KEY`  | `your-api-key`                                         |
| `GEMINI_ENDPOINT` | `https://generativelanguage.googleapis.com/v1beta/...` |

---


Absolutely! Here's an **expanded section** in your `README.md` that shows **how to use the API in different programming languages** — Java, JavaScript, Python, and C++.

---

## 🧩 How to Use the API in Your Project

Use this API in any app that can make HTTP requests.
Here are usage examples for **Java**, **JavaScript**, **Python**, and **C++**:

---

### ☕ Java (Spring Boot or Plain)

```java
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GeminiClient {
    public static void main(String[] args) {
        String prompt = "Tell me a joke.";
        String apiUrl = UriComponentsBuilder
                .fromHttpUrl("https://ai-chatbot-0mmy.onrender.com/gemini/chat")
                .queryParam("prompt", prompt)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(apiUrl, null, String.class);
        System.out.println("Gemini: " + response);
    }
}
```

---

### 🌐 JavaScript (Browser / Node.js)

```javascript
async function chatWithGemini(prompt) {
  const url = `https://ai-chatbot-0mmy.onrender.com/gemini/chat?prompt=${encodeURIComponent(prompt)}`;
  const response = await fetch(url, { method: "POST" });
  const text = await response.text();
  console.log("Gemini:", text);
}

chatWithGemini("What's the capital of France?");
```

---

### 🐍 Python (requests)

```python
import requests

prompt = "What is the meaning of life?"
url = f"https://ai-chatbot-0mmy.onrender.com/gemini/chat?prompt={prompt}"

response = requests.post(url)
print("Gemini:", response.text)
```

---

### 🧾 C++ (libcurl)

```cpp
#include <iostream>
#include <curl/curl.h>

size_t write_callback(void *contents, size_t size, size_t nmemb, std::string *s) {
    size_t new_length = size * nmemb;
    s->append((char *)contents, new_length);
    return new_length;
}

int main() {
    CURL *curl = curl_easy_init();
    if (curl) {
        std::string response;
        std::string prompt = "Hello Gemini!";
        std::string url = "https://ai-chatbot-0mmy.onrender.com/gemini/chat?prompt=" + curl_easy_escape(curl, prompt.c_str(), prompt.length());

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_POST, 1L);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, write_callback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);

        CURLcode res = curl_easy_perform(curl);
        if (res == CURLE_OK) {
            std::cout << "Gemini: " << response << std::endl;
        } else {
            std::cerr << "Request failed: " << curl_easy_strerror(res) << std::endl;
        }

        curl_easy_cleanup(curl);
    }
    return 0;
}
```

> 💡 Don't forget to link with `-lcurl` when compiling C++:

```bash
g++ gemini_client.cpp -lcurl -o gemini_client
```

---

## 🤝 Contributing

Pull requests are welcome!
For major changes, please open an issue first to discuss what you'd like to change.

---

## 📄 License

MIT License

```

