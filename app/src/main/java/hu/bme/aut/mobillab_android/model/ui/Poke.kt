package hu.bme.aut.mobillab_android.model.ui

import hu.bme.aut.mobillab_android.model.domain.PokeDetail

data class Poke (
    val id: Int,
    val name: String? = null,
    val hp: Int? = null,
    val atk: Int? = null,
    val def: Int? = null,
    val sp: Int? = null,
    val back_default: String? = null,
    val back_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val typeSlotOne: String? = null,
    val typeSlotTwo: String? = null,
    var isFavourite: Boolean = false
){
    companion object{
        fun convertDomainPokeDetailToUiPoke(pokeDetail: PokeDetail): Poke {
            var hp = 0
            var attack = 0
            var defense = 0
            var spAttack = 0

            if (pokeDetail.stats != null) {
                for (stat in pokeDetail.stats) {
                    val value = stat.base_stat
                    if (value != null) {
                        when (stat.stat?.name) {
                            "hp" -> hp = value
                            "attack" -> attack = value
                            "defense" -> defense = value
                            "special-attack" -> spAttack = value
                        }
                    }

                }
            }

            return Poke(
                id = pokeDetail.id,
                name = pokeDetail.name,
                hp = hp,
                atk = attack,
                def = defense,
                sp = spAttack,
                back_default = pokeDetail.sprites?.back_default,
                back_female = pokeDetail.sprites?.back_female,
                front_default = pokeDetail.sprites?.front_default,
                front_female = pokeDetail.sprites?.front_female,
                typeSlotOne = pokeDetail.types?.get(0)?.type?.name,
                typeSlotTwo = if(pokeDetail.types?.size == 2) pokeDetail.types[1].type?.name else null
            )
        }

        fun convertDomainPokeDetailToUiPoke(pokeDetail: PokeDetail, isFavourite: Boolean): Poke {
            val poke = convertDomainPokeDetailToUiPoke(pokeDetail)
            poke.isFavourite = isFavourite
            return poke
        }
    }

}
