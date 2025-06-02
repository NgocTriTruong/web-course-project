document.querySelectorAll('.btn-update-status').forEach(button => {
    button.addEventListener('click', async () => {
        const ghnOrderCode = button.getAttribute('data-ghn-code');
        const orderId = button.getAttribute('data-order-id');
        console.log(`Clicked update status for order ${orderId}, GHN code: ${ghnOrderCode}`);
        try {
            const response = await fetch(`${contextPath}/update-order-status`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ ghnOrderCode, orderId })
            });
            const data = await response.json();
            console.log(`Update response: ${JSON.stringify(data)}`);
            if (data.success) {
                alert(`Trạng thái đơn hàng: ${data.statusDisplay}`);
                const statusElement = button.closest('.order-item').querySelector('.order-status');
                statusElement.textContent = data.statusDisplay;
                statusElement.className = `order-status status-${data.status}`;
                if (data.status !== 1 || data.status !== 2) {
                    const cancelButton = button.closest('.order-item').querySelector('.btn-cancel-order');
                    if (cancelButton) cancelButton.remove();
                }
            } else {
                alert(`Lỗi: ${data.message}`);
            }
        } catch (error) {
            console.error('Error updating status:', error);
            alert('Lỗi khi cập nhật trạng thái.');
        }
    });
});

document.querySelectorAll('.btn-cancel-order').forEach(button => {
    button.addEventListener('click', async () => {
        if (confirm('Bạn có chắc chắn muốn hủy đơn hàng này?')) {
            const ghnOrderCode = button.getAttribute('data-ghn-code');
            const orderId = button.getAttribute('data-order-id');
            console.log(`Clicked cancel order for order ${orderId}, GHN code: ${ghnOrderCode}`);
            try {
                const response = await fetch(`${window.location.origin}/cancel-order-ghn`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ ghnOrderCode, orderId })
                });
                const data = await response.json();
                console.log(`Cancel response: ${JSON.stringify(data)}`);
                if (data.success) {
                    alert('Hủy đơn hàng thành công');
                    const statusElement = button.closest('.order-item').querySelector('.order-status');
                    statusElement.textContent = 'Đã hủy';
                    statusElement.className = 'order-status status-0';
                    button.remove();
                } else {
                    alert(`Lỗi: ${data.message}`);
                }
            } catch (error) {
                console.error('Error cancelling order:', error);
                alert('Lỗi khi hủy đơn hàng.');
            }
        }
    });
});