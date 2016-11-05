package com.simplerssreader.di;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MockNetworkModule.class)
public interface TestingComponent {

}
