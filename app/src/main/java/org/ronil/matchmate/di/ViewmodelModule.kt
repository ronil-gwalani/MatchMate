package org.ronil.matchmate.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.ronil.matchmate.viewmodels.HomeVM
import org.ronil.matchmate.viewmodels.LikesVM
import org.ronil.matchmate.viewmodels.MatchesVM
import org.ronil.matchmate.viewmodels.RegistrationVM

val ViewModelModule = module {
    viewModelOf(::HomeVM)
    viewModelOf(::LikesVM)
    viewModelOf(::RegistrationVM)
    viewModelOf(::MatchesVM)
//    viewModelOf(::UserDetailVM)
}