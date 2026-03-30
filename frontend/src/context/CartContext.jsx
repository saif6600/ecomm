import { createContext, useContext, useMemo, useState } from 'react';
import api from '../api/client';

const CartContext = createContext(null);

export function CartProvider({ children }) {
  const [cart, setCart] = useState({ items: [], total: 0 });

  const refreshCart = async () => {
    const { data } = await api.get('/cart');
    setCart(data);
  };

  const addToCart = async (productId, quantity = 1) => {
    const { data } = await api.post('/cart', { productId, quantity });
    setCart(data);
  };

  const updateCartItem = async (cartItemId, quantity) => {
    const { data } = await api.put(`/cart/${cartItemId}`, { quantity });
    setCart(data);
  };

  const removeCartItem = async (cartItemId) => {
    const { data } = await api.delete(`/cart/${cartItemId}`);
    setCart(data);
  };

  const value = useMemo(() => ({ cart, refreshCart, addToCart, updateCartItem, removeCartItem }), [cart]);
  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export const useCart = () => useContext(CartContext);
