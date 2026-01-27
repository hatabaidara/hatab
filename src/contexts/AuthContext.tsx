import { createContext, useContext, useState, ReactNode } from "react";

interface User {
  id: string;
  email: string;
  name: string;
  role: "admin";
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<boolean>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Simulated admin credentials
const ADMIN_CREDENTIALS = [
  { id: "1", email: "shaoumaidara@gmail.com", password: "admin123", name: "Administrateur Principal", role: "admin" as const },
  { id: "2", email: "shaoumaidara@gmail.com", password: "gestionnaire123", name: "Gestionnaire", role: "admin" as const },
];

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(() => {
    const stored = localStorage.getItem("darsalam_user");
    return stored ? JSON.parse(stored) : null;
  });

  const login = async (email: string, password: string): Promise<boolean> => {
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 500));
    
    const found = ADMIN_CREDENTIALS.find(
      cred => cred.email === email && cred.password === password
    );
    
    if (found) {
      const userData: User = {
        id: found.id,
        email: found.email,
        name: found.name,
        role: found.role,
      };
      setUser(userData);
      localStorage.setItem("darsalam_user", JSON.stringify(userData));
      return true;
    }
    return false;
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("darsalam_user");
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated: !!user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
