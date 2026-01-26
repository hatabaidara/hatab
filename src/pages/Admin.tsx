import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { 
  LogOut, 
  Home, 
  Newspaper, 
  Image, 
  MessageSquare, 
  Users,
  Plus,
  Edit,
  Trash2,
  Eye
} from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

// Simulated data
const mockNews = [
  { id: 1, title: "Célébration du Gamou 2024", date: "2024-01-15", status: "published" },
  { id: 2, title: "Lancement du projet agricole", date: "2024-01-10", status: "published" },
  { id: 3, title: "Formation des jeunes", date: "2024-01-05", status: "draft" },
];

const mockMessages = [
  { id: 1, name: "Amadou Diallo", email: "amadou@email.com", subject: "Partenariat agricole", date: "2024-01-14", read: false },
  { id: 2, name: "ONG Développement", email: "contact@ong.org", subject: "Demande de financement", date: "2024-01-12", read: true },
  { id: 3, name: "Marie Sow", email: "marie@email.com", subject: "Visite du village", date: "2024-01-10", read: true },
];

const mockGallery = [
  { id: 1, title: "Mosquée principale", category: "Spiritualité", date: "2024-01-08" },
  { id: 2, title: "Champs de riz", category: "Agriculture", date: "2024-01-05" },
  { id: 3, title: "Réunion des jeunes", category: "Jeunesse", date: "2024-01-03" },
];

const Admin = () => {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState("dashboard");

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, navigate]);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="min-h-screen bg-muted">
      {/* Header */}
      <header className="bg-background border-b border-border sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center gap-4">
            <h1 className="font-serif text-xl font-bold text-primary">
              Darsalam Chérif - Admin
            </h1>
          </div>
          <div className="flex items-center gap-4">
            <span className="text-sm text-muted-foreground hidden md:block">
              {user?.name}
            </span>
            <Button variant="outline" size="sm" onClick={() => navigate("/")}>
              <Home className="w-4 h-4 mr-2" />
              Voir le site
            </Button>
            <Button variant="destructive" size="sm" onClick={handleLogout}>
              <LogOut className="w-4 h-4 mr-2" />
              Déconnexion
            </Button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8">
        <Tabs value={activeTab} onValueChange={setActiveTab} className="space-y-6">
          <TabsList className="grid w-full grid-cols-4 lg:w-auto lg:inline-flex">
            <TabsTrigger value="dashboard" className="gap-2">
              <Users className="w-4 h-4" />
              <span className="hidden sm:inline">Tableau de bord</span>
            </TabsTrigger>
            <TabsTrigger value="news" className="gap-2">
              <Newspaper className="w-4 h-4" />
              <span className="hidden sm:inline">Actualités</span>
            </TabsTrigger>
            <TabsTrigger value="gallery" className="gap-2">
              <Image className="w-4 h-4" />
              <span className="hidden sm:inline">Galerie</span>
            </TabsTrigger>
            <TabsTrigger value="messages" className="gap-2">
              <MessageSquare className="w-4 h-4" />
              <span className="hidden sm:inline">Messages</span>
            </TabsTrigger>
          </TabsList>

          {/* Dashboard Tab */}
          <TabsContent value="dashboard" className="space-y-6">
            <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
              <Card>
                <CardHeader className="pb-2">
                  <CardDescription>Actualités publiées</CardDescription>
                  <CardTitle className="text-3xl">{mockNews.filter(n => n.status === "published").length}</CardTitle>
                </CardHeader>
              </Card>
              <Card>
                <CardHeader className="pb-2">
                  <CardDescription>Images dans la galerie</CardDescription>
                  <CardTitle className="text-3xl">{mockGallery.length}</CardTitle>
                </CardHeader>
              </Card>
              <Card>
                <CardHeader className="pb-2">
                  <CardDescription>Messages non lus</CardDescription>
                  <CardTitle className="text-3xl">{mockMessages.filter(m => !m.read).length}</CardTitle>
                </CardHeader>
              </Card>
              <Card>
                <CardHeader className="pb-2">
                  <CardDescription>Total messages</CardDescription>
                  <CardTitle className="text-3xl">{mockMessages.length}</CardTitle>
                </CardHeader>
              </Card>
            </div>

            <Card>
              <CardHeader>
                <CardTitle>Activité récente</CardTitle>
                <CardDescription>Les dernières actions sur le site</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex items-center gap-4 p-3 bg-muted rounded-lg">
                    <div className="w-10 h-10 bg-primary/10 rounded-full flex items-center justify-center">
                      <MessageSquare className="w-5 h-5 text-primary" />
                    </div>
                    <div>
                      <p className="font-medium">Nouveau message reçu</p>
                      <p className="text-sm text-muted-foreground">Amadou Diallo - il y a 2 heures</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4 p-3 bg-muted rounded-lg">
                    <div className="w-10 h-10 bg-secondary/10 rounded-full flex items-center justify-center">
                      <Newspaper className="w-5 h-5 text-secondary" />
                    </div>
                    <div>
                      <p className="font-medium">Actualité publiée</p>
                      <p className="text-sm text-muted-foreground">Célébration du Gamou 2024 - il y a 1 jour</p>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </TabsContent>

          {/* News Tab */}
          <TabsContent value="news" className="space-y-6">
            <div className="flex justify-between items-center">
              <h2 className="text-2xl font-serif font-bold">Gestion des actualités</h2>
              <Button>
                <Plus className="w-4 h-4 mr-2" />
                Nouvelle actualité
              </Button>
            </div>
            <Card>
              <CardContent className="pt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Titre</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Statut</TableHead>
                      <TableHead className="text-right">Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {mockNews.map((news) => (
                      <TableRow key={news.id}>
                        <TableCell className="font-medium">{news.title}</TableCell>
                        <TableCell>{news.date}</TableCell>
                        <TableCell>
                          <Badge variant={news.status === "published" ? "default" : "secondary"}>
                            {news.status === "published" ? "Publié" : "Brouillon"}
                          </Badge>
                        </TableCell>
                        <TableCell className="text-right">
                          <div className="flex justify-end gap-2">
                            <Button variant="ghost" size="icon">
                              <Eye className="w-4 h-4" />
                            </Button>
                            <Button variant="ghost" size="icon">
                              <Edit className="w-4 h-4" />
                            </Button>
                            <Button variant="ghost" size="icon" className="text-destructive">
                              <Trash2 className="w-4 h-4" />
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>

          {/* Gallery Tab */}
          <TabsContent value="gallery" className="space-y-6">
            <div className="flex justify-between items-center">
              <h2 className="text-2xl font-serif font-bold">Gestion de la galerie</h2>
              <Button>
                <Plus className="w-4 h-4 mr-2" />
                Ajouter une image
              </Button>
            </div>
            <Card>
              <CardContent className="pt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Titre</TableHead>
                      <TableHead>Catégorie</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead className="text-right">Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {mockGallery.map((image) => (
                      <TableRow key={image.id}>
                        <TableCell className="font-medium">{image.title}</TableCell>
                        <TableCell>
                          <Badge variant="outline">{image.category}</Badge>
                        </TableCell>
                        <TableCell>{image.date}</TableCell>
                        <TableCell className="text-right">
                          <div className="flex justify-end gap-2">
                            <Button variant="ghost" size="icon">
                              <Eye className="w-4 h-4" />
                            </Button>
                            <Button variant="ghost" size="icon">
                              <Edit className="w-4 h-4" />
                            </Button>
                            <Button variant="ghost" size="icon" className="text-destructive">
                              <Trash2 className="w-4 h-4" />
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>

          {/* Messages Tab */}
          <TabsContent value="messages" className="space-y-6">
            <h2 className="text-2xl font-serif font-bold">Messages reçus</h2>
            <Card>
              <CardContent className="pt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Expéditeur</TableHead>
                      <TableHead>Sujet</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Statut</TableHead>
                      <TableHead className="text-right">Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {mockMessages.map((message) => (
                      <TableRow key={message.id} className={!message.read ? "bg-primary/5" : ""}>
                        <TableCell>
                          <div>
                            <p className="font-medium">{message.name}</p>
                            <p className="text-sm text-muted-foreground">{message.email}</p>
                          </div>
                        </TableCell>
                        <TableCell className="font-medium">{message.subject}</TableCell>
                        <TableCell>{message.date}</TableCell>
                        <TableCell>
                          <Badge variant={message.read ? "secondary" : "default"}>
                            {message.read ? "Lu" : "Non lu"}
                          </Badge>
                        </TableCell>
                        <TableCell className="text-right">
                          <div className="flex justify-end gap-2">
                            <Button variant="ghost" size="icon">
                              <Eye className="w-4 h-4" />
                            </Button>
                            <Button variant="ghost" size="icon" className="text-destructive">
                              <Trash2 className="w-4 h-4" />
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>
      </main>
    </div>
  );
};

export default Admin;
