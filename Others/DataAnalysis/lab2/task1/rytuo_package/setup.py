from setuptools import setup

setup(
    name='rytuo_package',
    version='0.1.0',
    description='An example Python package',
    url='https://github.com/Rytuo/rytuo_package',
    author='Aleksandr Popov',
    author_email='popov.aleksandt.v@yandex.ru',
    license='MIT',
    packages=['rytuo_package'],
    install_requires=['numpy',
                      'matplotlib',
                      'typing'
                      ],

    classifiers=[
        'Development Status :: 1 - Planning',
        'Operating System :: POSIX :: Linux',
        'Programming Language :: Python :: 3',
    ],
)