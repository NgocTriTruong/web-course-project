// File: voiceSearch.js
document.addEventListener('DOMContentLoaded', function() {
    // Xác định xem trình duyệt có hỗ trợ API Speech Recognition không
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

    if (!SpeechRecognition) {
        console.error("Trình duyệt của bạn không hỗ trợ nhận dạng giọng nói");
        return;
    }

    // Tìm form tìm kiếm và các input
    const searchForm = document.querySelector('#search-form-container form');
    const descriptionInput = document.querySelector('input[name="description"]');
    const minPriceInput = document.querySelector('input[name="minPrice"]');
    const maxPriceInput = document.querySelector('input[name="maxPrice"]');
    const voiceSearchButton = document.querySelector('.voice-search-btn');

    if (!searchForm || !descriptionInput || !voiceSearchButton) {
        console.error('Không tìm thấy form tìm kiếm, input mô tả hoặc nút voice search');
        return;
    }

    // Tạo phần tử hiển thị trạng thái
    const statusElement = document.createElement('div');
    statusElement.className = 'voice-search-status';
    statusElement.innerHTML = `
        <div class="status-title">Đang lắng nghe...</div>
        <div class="listening-animation">
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
            <div class="bar"></div>
        </div>
        <div class="recognition-result"></div>
        <div class="hint" style="margin-top: 10px; font-size: 14px;">Nói "giá" + số tiền để tìm theo khoảng giá</div>
    `;
    document.body.appendChild(statusElement);

    const resultDiv = statusElement.querySelector('.recognition-result');

    // Khởi tạo Web Speech API
    const recognition = new SpeechRecognition();
    recognition.lang = 'vi-VN'; // Đặt ngôn ngữ tiếng Việt
    recognition.continuous = false;
    recognition.interimResults = true;

    let isListening = false;

    // Xử lý sự kiện khi nhấn nút tìm kiếm bằng giọng nói
    voiceSearchButton.addEventListener('click', function() {
        if (isListening) {
            recognition.stop();
            return;
        }

        // Bắt đầu nhận dạng
        try {
            recognition.start();
            isListening = true;
            voiceSearchButton.classList.add('listening');
            statusElement.style.display = 'block';
            resultDiv.textContent = '';
        } catch (err) {
            console.error('Lỗi khi bắt đầu nhận dạng giọng nói:', err);
        }
    });

    // Xử lý kết quả nhận dạng tạm thời
    recognition.onresult = function(event) {
        const transcript = Array.from(event.results)
            .map(result => result[0].transcript)
            .join('');

        resultDiv.textContent = transcript;

        if (event.results[0].isFinal) {
            // Xử lý văn bản đã nhận dạng
            processVoiceCommand(transcript);
        }
    };

    // Xử lý văn bản tìm kiếm bằng giọng nói
    function processVoiceCommand(text) {
        // Chuẩn hóa văn bản: loại bỏ dấu cách thừa, chuyển thành chữ thường
        text = text.toLowerCase().trim().replace(/\s+/g, ' ').replace(/\.$/, '');

        // Mẫu regex để tìm giá
        const minPriceMatch = text.match(/(?:giá\s*(từ|tối thiểu|thấp nhất|nhỏ nhất|thấp|nhỏ|min)\s*)(\d+)/i);
        const maxPriceMatch = text.match(/(?:giá\s*(đến|tối đa|cao nhất|lớn nhất|cao|lớn|max)\s*)(\d+)/i);
        const priceRangeMatch = text.match(/giá\s*(\d+)\s*đến\s*(\d+)/i);

        // Điền giá vào input
        if (priceRangeMatch) {
            minPriceInput.value = priceRangeMatch[1];
            maxPriceInput.value = priceRangeMatch[2];
        } else {
            if (minPriceMatch) {
                minPriceInput.value = minPriceMatch[2];
            }
            if (maxPriceMatch) {
                maxPriceInput.value = maxPriceMatch[2];
            }
        }

        // Loại bỏ tất cả các từ liên quan đến giá khỏi văn bản
        let cleanText = text
            .replace(/(giá\s*(từ|tối thiểu|thấp nhất|nhỏ nhất|thấp|nhỏ|min|đến|tối đa|cao nhất|lớn nhất|cao|lớn|max)\s*\d*)/gi, '') // Loại từ khóa giá và số
            .replace(/giá\s*\d+\s*đến\s*\d+/gi, '') // Loại khoảng giá
            .replace(/\s+/g, ' ') // Chuẩn hóa dấu cách
            .trim()
            .replace(/\.$/, ''); // Loại bỏ dấu chấm cuối cùng

        // Điền mô tả vào input
        descriptionInput.value = cleanText;
    }

    // Xử lý khi kết thúc nhận dạng
    recognition.onend = function() {
        isListening = false;
        voiceSearchButton.classList.remove('listening');
        statusElement.style.display = 'none';

        // Nếu có nội dung trong ô tìm kiếm, tự động submit form sau 1.5 giây
        if (descriptionInput.value.trim() !== '' || minPriceInput.value !== '' || maxPriceInput.value !== '') {
            setTimeout(() => {
                searchForm.submit();
            }, 1500);
        }
    };

    // Xử lý lỗi
    recognition.onerror = function(event) {
        console.error('Lỗi nhận dạng giọng nói:', event.error);
        isListening = false;
        voiceSearchButton.classList.remove('listening');
        statusElement.style.display = 'none';
        alert('Có lỗi xảy ra khi nhận dạng giọng nói. Vui lòng thử lại.');
    };
});