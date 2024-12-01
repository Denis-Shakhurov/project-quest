@import dto.StatisticPage
@import utils.NamedRoutes
@param StatisticPage page

@template.layout.page(
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-fluid bg-light p-5">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-dark">
                        <h1 class="display-3 mb-0">Статистика игр</h1>
                        @if(page.getGames().isEmpty())
                            <p>Пока не сыграно ни в одну игру</p>
                        @endif
                        <table class="table table-bordered table-hover mt-3">
                            <thead>
                            <tr>
                                <th scope="col">id</th>
                                <th scope="col">Имя игрока</th>
                                <th scope="col">Название игры</th>
                                <th scope="col">Количество побед</th>
                                <th scope="col">Количество поражений</th>
                            </tr>
                        </thead>
                            @for(var user : page.getUsers())
                                @for(var game : page.getGames().stream().filter(g -> g.getUserId().equals(user.getId())).toList())
                                    <tbody>
                                    <tr>
                                        <th scope="row">${user.getId()}</th>
                                        <td><a href="${NamedRoutes.userPath(user.getId())}">${user.getName()}</a></td>
                                        <th scope="row">${game.getName()}</th>
                                        <th scope="row">${game.getCountWin()}</th>
                                        <th scope="row">${game.getCountLose()}</th>
                                    </tr>
                                    </tbody>
                                @endfor
                            @endfor
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </main>
`
)
