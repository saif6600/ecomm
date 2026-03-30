import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';

export default function CartPage() {
  const { cart, refreshCart, updateCartItem, removeCartItem } = useCart();

  useEffect(() => { refreshCart().catch(() => {}); }, []);

  return (
    <div>
      <h2 className="text-2xl font-semibold mb-4">Cart</h2>
      <div className="space-y-3">
        {cart.items.map((item) => (
          <div key={item.cartItemId} className="bg-white border rounded p-4 flex justify-between items-center">
            <div>
              <p className="font-semibold">{item.productName}</p>
              <p>${item.price} x {item.quantity}</p>
            </div>
            <div className="flex gap-2">
              <button className="px-2 border" onClick={() => updateCartItem(item.cartItemId, item.quantity + 1)}>+</button>
              <button className="px-2 border" onClick={() => item.quantity > 1 && updateCartItem(item.cartItemId, item.quantity - 1)}>-</button>
              <button className="px-2 bg-red-500 text-white" onClick={() => removeCartItem(item.cartItemId)}>Remove</button>
            </div>
          </div>
        ))}
      </div>
      <p className="text-xl mt-6">Total: ${cart.total}</p>
      <Link to="/checkout" className="inline-block mt-3 bg-green-600 text-white px-4 py-2 rounded">Checkout</Link>
    </div>
  );
}
