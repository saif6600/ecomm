import { Link } from 'react-router-dom';

export default function HomePage() {
  return (
    <section className="text-center py-20">
      <h1 className="text-4xl font-bold mb-4">Modern E-Commerce Starter</h1>
      <p className="text-gray-600 mb-6">Secure JWT auth, cart, checkout, and admin controls.</p>
      <Link to="/products" className="bg-blue-600 text-white px-6 py-3 rounded">Shop Now</Link>
    </section>
  );
}
