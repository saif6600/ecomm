import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../api/client';
import { useCart } from '../context/CartContext';

export default function ProductDetailPage() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [qty, setQty] = useState(1);
  const [error, setError] = useState('');
  const { addToCart } = useCart();

  useEffect(() => {
    api.get(`/products/${id}`).then(({ data }) => setProduct(data));
  }, [id]);

  const handleAdd = async () => {
    try {
      setError('');
      await addToCart(product.id, qty);
      alert('Added to cart');
    } catch (e) {
      setError(e.response?.data?.error || 'Please login to add items');
    }
  };

  if (!product) return <p>Loading...</p>;

  return (
    <div className="bg-white border rounded p-6">
      <h2 className="text-2xl font-bold">{product.name}</h2>
      <p className="text-gray-600 my-2">{product.description}</p>
      <p className="text-xl font-semibold">${product.price}</p>
      <div className="mt-4 flex gap-2">
        <input type="number" min={1} className="border p-2 w-24" value={qty} onChange={(e) => setQty(Number(e.target.value))} />
        <button className="bg-blue-600 text-white px-4 py-2 rounded" onClick={handleAdd}>Add to Cart</button>
      </div>
      {error && <p className="text-red-600 mt-2">{error}</p>}
    </div>
  );
}
