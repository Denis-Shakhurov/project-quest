@import dto.StatisticPage
@param StatisticPage page

@template.layout.page(
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1>Статистика игр</h1>
                @if(page.getGames().isEmpty())
                    <p>Пока не сыграно ни в одну игру</p>
                @endif
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
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
                            <th scope="row">${page.getUsers().indexOf(user) + 1}</th>
                            <td><a href="/users/${(user.getId())}">${user.getName()}</a></td>
                            <th scope="row">${game.getName()}</th>
                            <th scope="row">${game.getCountWin()}</th>
                            <th scope="row">${game.getCountLose()}</th>
                        </tr>
                        </tbody>
                        @endfor
                    @endfor
                </table>
            </div>
        </section>
    </main>
`
)
