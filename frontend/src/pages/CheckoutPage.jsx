import { useState } from 'react';
import api from '../api/client';

export default function CheckoutPage() {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const placeOrder = async () => {
    setLoading(true);
    try {
      const { data } = await api.post('/orders');
      setMessage(`Order #${data.id} placed successfully!`);
    } catch (e) {
      setMessage(e.response?.data?.error || 'Unable to place order');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white border rounded p-6">
      <h2 className="text-2xl font-bold mb-4">Checkout</h2>
      <button onClick={placeOrder} disabled={loading} className="bg-blue-600 text-white px-5 py-2 rounded disabled:opacity-50">
        {loading ? 'Processing...' : 'Place Order'}
      </button>
      {message && <p className="mt-4">{message}</p>}
    </div>
  );
}
