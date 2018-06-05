from flask import *
from peewee import *

app = Flask(__name__)

musa_db = MySQLDatabase(
    "musa", host="localhost", port=3306, user="euterpe", passwd="An6248322")

class MySQLModel(Model):
    """Database model"""
    class Meta:
        database = musa_db

class Melomano(MySQLModel):
    nombreUsuario = CharField()
    nombre = CharField()
    apPaterno = CharField()
    apMaterno = CharField()
    password = CharField()
    fotoPerfil = BlobField()
    correoElectronico = CharField()

class Genero(MySQLModel):
    idGenero = PrimaryKeyField()
    genero = CharField()

class Artista(MySQLModel):
    idArtista = PrimaryKeyField()
    nombre = CharField()
    biografia = CharField()
    genero = CharField()
    correoElectronico = CharField()

class Album(MySQLModel):
    idAlbum = PrimaryKeyField()
    nombre = CharField()
    portada = BlobField()
    fechaLanzamiento = DateField()
    companiaDiscografica = CharField()
    idArtista = ForeignKeyField(Artista, db_column = "idArtista")

class Cancion(MySQLModel):
    idCancion = PrimaryKeyField()
    nombre = CharField()
    idAlbum = ForeignKeyField(Album, db_column = "idAlbum")
    idGenero = ForeignKeyField(Genero, db_column = "idGenero")
    cancion = BlobField()
    duracion = IntegerField()

class Playlist(MySQLModel):
    idPlaylist = PrimaryKeyField()
    nombre = CharField()
    portada = BlobField()
    nombreUsuario = ForeignKeyField(Melomano, db_column = "nombreUsuario")

class CancionesPlaylist(MySQLModel):
    idPlaylist = ForeignKeyField(Playlist, db_column = "idPlaylist")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

class Calificacion(MySQLModel):
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")
    nombreUsuario = ForeignKeyField(Melomano, db_column = "nombreUsuario")
    calificacion = IntegerField()

class CancionPropia(MySQLModel):
    idCancionPropia = PrimaryKeyField()
    nombre = CharField()
    cancion = BlobField()
    nombreUsuario = ForeignKeyField(Melomano, db_column = "nombreUsuario")

class FotoArtista(MySQLModel):
    idFoto = PrimaryKeyField()
    foto = BlobField()
    idArtista = ForeignKeyField(Artista, db_column = "idArtista")

class Historial(MySQLModel):
    nombreUsuario = ForeignKeyField(Melomano, db_column = "nombreUsuario")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

def autenticar_melomano(melomano):
    session['logged_in'] = True
    session['username'] = melomano.nombreUsuario

@app.route("/")
def main():
    return jsonify("Musa server. versión 1.0")

@app.route("/melomanos/admin/agregar", methods=["POST"])
def registrar_melomano():
    with musa_db.atomic():
        try:
            melomano = Melomano.create(
                nombreUsuario = request.form['nombreUsuario'],
                nombre = request.form['nombre'],
                apPaterno = request.form['apPaterno'],
                apMaterno = request.form['apMaterno'],
                password = request.form['password'],
                fotoPerfil = request.form['fotoPerfil'],
                correoElectronico = request.form['correoElectronico'])
            return jsonify("Usuario registrado")
        except IntegrityError :
            return jsonify("Ocurrió un error")

if __name__ == "__main__":
    app.run(host = '192.168.0.16', port = '5555', debug = True)
