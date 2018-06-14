
from flask import *
from peewee import *
import sys
from playhouse.shortcuts import model_to_dict, dict_to_model
from base64 import b64encode

app = Flask(__name__)

musa_db = MySQLDatabase(
    "musa", host="localhost", port=3306, user="euterpe", passwd="An6248322")

class MySQLModel(Model):
    """Database model"""
    class Meta:
        database = musa_db
# Mensajes
# 5 - Todo PERFECTO
# 51 - Melomano login
# 52 - Artista login
# 7 - Todo mal
# 1 - Contraseña incorrecta
# 2 - El usuario no existe
# 3 - Lista vacia
# 4 - Usuario registrado
# 6 - El usuario ya existe
# 10 - Álbum registrado
# 11 - Error al registrar álbum
# 12 - El artista de agregó
# 13 - Error al agregar al artista
# 14 - Se agregó la canción
# 15 - No se pudo agregar la canción
# 16 - Se actualizó el artista
# 17 - Error al actualizar el artista
# 18 - Se subió la foto
# 19 - Error al subir la foto
# 300 - Contraseñas no coinciden

class Melomano(MySQLModel):
    idMelomano = PrimaryKeyField()
    nombreMelomano = CharField()
    nombre = CharField()
    apellidos = CharField()
    password = CharField()
    fotoPerfil = TextField()
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
    password = CharField()

class Album(MySQLModel):
    idAlbum = PrimaryKeyField()
    nombre = CharField()
    portada = TextField()
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
    portada = TextField()
    idMelomano = ForeignKeyField(Melomano, db_column = "idMelomano")

class CancionesPlaylist(MySQLModel):
    idPlaylist = ForeignKeyField(Playlist, db_column = "idPlaylist")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

class Calificacion(MySQLModel):
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")
    calificacion = IntegerField()

class CancionPropia(MySQLModel):
    idCancionPropia = PrimaryKeyField()
    nombre = CharField()
    cancion = BlobField()
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")

class FotoArtista(MySQLModel):
    idFoto = PrimaryKeyField()
    foto = TextField()
    idArtista = ForeignKeyField(Artista, db_column = "idArtista")

class Historial(MySQLModel):
    nombreUsuario = ForeignKeyField(Melomano, db_column = "idMelomano")
    idCancion = ForeignKeyField(Cancion, db_column = "idCancion")

@app.route("/")
def main():
    return jsonify("Musa server. versión 1.0")

@app.route("/melomano/agregar", methods=["POST"])
def registrar_melomano():
    with musa_db.atomic():
        try:
            melomano = Melomano.create(
                nombreMelomano = request.form['nombreMelomano'],
                nombre = request.form['nombre'],
                apellidos = request.form['apellidos'],
                password = request.form['password'],
                fotoPerfil = request.form['fotoPerfil'],
                correoElectronico = request.form['correoElectronico'])
            mensaje = 4
        except IntegrityError:
            mensaje = 6
    return jsonify(mensaje)

@app.route("/login", methods=["POST"])
def iniciar_sesion():
    mensaje = 2
    for melomano in Melomano.select():
        if (melomano.nombreMelomano == request.form['username']) & (melomano.password == request.form['password']):
            mensaje = 51
    
    for artista in Artista.select():
        if (artista.correoElectronico == request.form['username']) & (artista.password == request.form['password']):
            mensaje = 52
    
    return jsonify(mensaje)

@app.route("/melomano/recuperar", methods=["POST"])
def recuperarMelomano():
    melomano = Melomano.get(Melomano.nombreMelomano == request.form['nombreMelomano'])
    return jsonify(model_to_dict(melomano))

@app.route("/artista/agregar", methods=["POST"])
def agregar_artista():
    with musa_db.atomic():
        try:
            artista = Artista.create(
                nombre = request.form['nombre'],
                biografia = request.form['biografia'],
                genero = request.form['genero'],
                correoElectronico = request.form['correoElectronico'],
                password = request.form['password']
            )
            mensaje = 12
        except IntegrityError:
            mensaje = 13
    return jsonify(mensaje)

@app.route("/artista/actualizar", methods=["PUT"])
def actualizar_artista():
    try:
        artista = Artista.select().where(Artista.idArtista == request.form["idArtista"]).get()
        artista.biografia = request.form["biografia"]
        artista.save()
        mensaje = 16
    except IntegrityError:
        mensaje = 17
    return jsonify(mensaje)

@app.route("/artista/recuperarArtista", methods=["POST"])
def recuperar_artista():
    artista = Artista.get(Artista.correoElectronico == request.form["nombre"])
    return jsonify(model_to_dict(artista))

@app.route("/artista/subirFoto", methods=["POST"])
def subir_foto_artista():
    with musa_db.atomic():
        try:
            foto = FotoArtista.create(
                foto = request.form['foto'],
                idArtista = request.form['idArtista']
            )
            mensaje = 18
        except IntegrityError:
            mensaje = 19
        return jsonify(mensaje)

"""
@app.route("/artista/recuperarFotos", methods=["POST"])
def recuperar_fotos_artista():
    foto_query = FotoArtista.select().where(FotoArtista.idArtista == request["idArtista"])

    fotos = 
    aux = 1
    for picture in foto_query:
        dic_foto = {aux: {"idFoto": picture.idFoto, "foto": picture.foto}}
        aux += 1
        fotos.update(dic_foto)

    return jsonify(fotos)
"""


@app.route("/album/agregar", methods=["POST"])
def agregar_album():
    with musa_db.atomic():
        try:
            album = Album.create(
                nombre = request.form['nombre'],
                portada = request.form['portada'],
                fechaLanzamiento = request.form['fechaLanzamiento'],
                companiaDiscografica = request.form['companiaDiscografica'],
                idArtista = request.form['idArtista']
            )
            mensaje = 10
        except IntegrityError:
            mensaje = 11
    return jsonify(mensaje)

@app.route("/cancion/agregar", methods=["POST"])
def agregar_cancion():
    with musa_db.atomic():
        try:
            cancion = Cancion.create(
                nombre = request.form['nombre'],
                idAlbum = request.form['idAlbum'],
                idGenero = request.form['idGenero'],
                cancion = request.form['cancion'],
                duracion = request.form['duracion'],
            )
            mensaje = 14
        except IntegrityError:
            mensaje = 15
    return jsonify(mensaje)


@app.route("/canciones/cancionesArtista", methods=["POST"])
def recuperar_canciones_artista():
    query = Cancion.select().join(Album).join(Artista).where(Artista.nombre == request.form["nombreArtista"])

    songs = {}
    for cancion in query:
        song = {cancion.idCancion: {"idCancion": cancion.idCancion, "nombre": cancion.nombre, "artista": cancion.idAlbum.idArtista.nombre,
                "album":cancion.idAlbum.nombre, "duracion": cancion.duracion}}
        songs.update(song)
    
    return jsonify(songs)

@app.route("/canciones/buscar", methods=["POST"])
def buscar_canciones():
    query = Cancion.select().join(Album).join(Artista).where(Artista.nombre.contains(request.form["nombre"]) | 
    (Cancion.nombre.contains(request.form["nombre"]) | 
    (Album.nombre.contains(request.form["nombre"]))))

    songs = {}
    aux = 1
    for cancion in query:
        song = {aux: {"idCancion": cancion.idCancion, "nombre": cancion.nombre, "artista": cancion.idAlbum.idArtista.nombre,
                "album":cancion.idAlbum.nombre, "duracion": cancion.duracion}}
        songs.update(song)
        aux += 1
    
    return jsonify(songs)

@app.route("/playlist/recuperarMelomano", methods=["POST"])
def recuperar_playlist():
    listas = Playlist.select().where(Playlist.idMelomano == request.form["idMelomano"])

    playlists = {}
    aux = 1
    for lista in listas:
        oneLista = {aux: {"idPlaylist": lista.idPlaylist, "nombre": lista.nombre}}
        aux += 1
        playlists.update(oneLista)

    return jsonify(playlists)

if __name__ == "__main__":
    app.run(host = '192.168.1.79', port = '5555', debug = True)
